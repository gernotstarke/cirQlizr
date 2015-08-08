package org.circulizr.ui

import org.circulizr.DigiNode
import org.circulizr.NumVizColor
import org.circulizr.NumberVisualizer
import org.circulizr.RunMode
import org.circulizr.numbers.Pair

import javax.swing.JPanel
import javax.swing.Scrollable
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.QuadCurve2D
import java.util.logging.Logger

// see end-of-file for license information


class DrawingCanvas extends JPanel {

    // size of drawing canvas in pixel-units
    private Integer X_CANVAS_SIZE
    private Integer Y_CANVAS_SIZE

    // to relocate the point-of-origin from upper-left (which is standard in Java2D)
    // to the center of the circle, we need a translation offset
    private Integer TRANSLATION_OFFSET


    private Integer LEGEND_WIDTH = 40

    // margin between circle and outer bound of canvas
    private Integer MARGIN = 20

    // program name and author, URL, shown at bottom of canvas
    private String INFO_LINE

    // entry point to the "domain" - in DDD-terms: AggregateRoot
    private NumberVisualizer nv

    // accounting which connectionPoint within which Segment is currently active
    private List<Integer> currentConnectionPointInSegment

    // default: development mode
    private RunMode RUNMODE = RunMode.PRODUCTION

    private static final Logger LOGGER = Logger.getLogger(DrawingCanvas.class.getName())



    DrawingCanvas(int x_resolution, int y_resolution, String infoLine, NumberVisualizer numberVisualizer, RunMode mode) {
        super()
        this.RUNMODE = mode

        initCanvas(x_resolution, y_resolution, infoLine, numberVisualizer)


    }


    private void initCanvas(int xFrameSize, int yFrameSize, String infoLine, NumberVisualizer numberVisualizer) {
        X_CANVAS_SIZE = xFrameSize
        Y_CANVAS_SIZE = yFrameSize

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        TRANSLATION_OFFSET = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE - MARGIN).intdiv(2)

        INFO_LINE = infoLine

        assert numberVisualizer != null
        nv = numberVisualizer

        currentConnectionPointInSegment = new ArrayList<Integer>(10)
    }

    /*
     * translates position of center to 0/0
     */

    private void translateCenterToZero(Graphics2D g2d) {
        g2d.translate(TRANSLATION_OFFSET, TRANSLATION_OFFSET)
    }

    /*
    * draw segments with their circular representation in a given radius
    */

    private void drawSegments(Graphics2D g2d) {

        // segments represented by Arc2D.Double or Arc2D.Float instances
        g2d.setStroke(new BasicStroke(12.0f))
        Arc2D arc2D = new Arc2D.Double()

        (0..9).each { digit ->
            nv.segment[digit].with {
                g2d.setPaint(color)
                //LOGGER.info "digit $digit: center.x=${center.x}, center.y=${center.y}"

                arc2D.setArcByCenter(0, 0, radius, Math.toDegrees(angleStart), Math.toDegrees(angleExtend), Arc2D.OPEN)
                g2d.draw(arc2D)

                if (RUNMODE < RunMode.PRODUCTION) {
                    // draw dot for all digiNode-instances
                    drawDotForDigiNodes(g2d, digiNode)
                }
            }
        }
    }

    /**
     *
     */
    private void drawDotForDigiNodes(Graphics2D g2d, List<DigiNode> nodes) {
        g2d.setPaint(Color.darkGray)
        nodes.each { node ->
            double xCoord = node.coordinate.x
            double yCoord = node.coordinate.y
            Ellipse2D nodeCircle = new Ellipse2D.Double(xCoord - 3, yCoord - 3, 6, 6)
            g2d.fill(nodeCircle)
        }
    }

    /*
   * draw the lines for all pairs
    */

    private void drawLines(Graphics2D g2d) {
        resetConnectionPoints()

        g2d.setStroke(new BasicStroke(1.0f))

        (0..nv.NR_OF_CONNECTIONS_TO_SHOW - 1).each { pairIndex ->
            Pair currentPair = nv.NUMBER.getPair(pairIndex)
            drawConnectionForNumberPair(g2d, currentPair.first, currentPair.second)
        }

    }

    private void resetConnectionPoints() {
        nv.segment.each {
            currentConnectionPointInSegment[it.digit] = 0
        }
    }

    /*
    draw line for a single getPair of numbers
     */

    private void drawConnectionForNumberPair(Graphics2D g2d, int fromDigit, int toDigit) {
        g2d.setColor(nv.segment[fromDigit].color)

        int fromDigiNodeIndex = nv.segment[fromDigit].getNextFreeDigiNode()
        int toDigiNodeIndex = nv.segment[toDigit].getNextFreeDigiNode()

        // QuadCurve arguments: startX, startY, ctrlX, ctrlY, endX, endY)
        g2d.draw(new QuadCurve2D.Double(
                nv.segment[fromDigit].digiNode[fromDigiNodeIndex].coordinate.getX(),
                nv.segment[fromDigit].digiNode[fromDigiNodeIndex].coordinate.getY(),
                0,0,
                nv.segment[toDigit].digiNode[toDigiNodeIndex].coordinate.getX(),
                nv.segment[toDigit].digiNode[toDigiNodeIndex].coordinate.getY())
                )

        //g2d.draw(new Line2D.Double(
        //        nv.segment[fromDigit].digiNode[fromDigiNodeIndex].coordinate.toPoint(),
        //        nv.segment[toDigit].digiNode[toDigiNodeIndex].coordinate.toPoint()))

        nv.segment[fromDigit].advanceToNextAvailableDigiNode()
        nv.segment[toDigit].advanceToNextAvailableDigiNode()

        //LOGGER.info "draw line from $fromDigit (${segment[fromDigit].digiNode[pairIndex]}"
    }

    /*
    * draw the legend
     */

    private void drawLegend(Graphics2D g2d) {

        // setup some nice font parameters for the legend
        initLegendFont(g2d)

        (0..9).each { digit ->
            // set color scheme
            g2d.setPaint(NumVizColor.color[digit])

            // draw filled rectangle
            g2d.fillRect((X_CANVAS_SIZE - TRANSLATION_OFFSET) - 55,
                    (Y_CANVAS_SIZE - TRANSLATION_OFFSET - 2 * MARGIN - (digit + 1) * 35), 30, 30)

            // show corresponding digit
            g2d.drawString(digit.toString(), (X_CANVAS_SIZE - TRANSLATION_OFFSET - 20),
                    (Y_CANVAS_SIZE - TRANSLATION_OFFSET - 2 * MARGIN - digit * 35 - 14))
        }
    }


    private void initLegendFont(Graphics2D g2d) {
        Font font = new Font(Font.SERIF, Font.TRUETYPE_FONT, 16);
        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        /* This colour applies just to the font, not other rendering */
        map.put(TextAttribute.FOREGROUND, Color.DARK_GRAY);

        font = font.deriveFont(map);
        g2d.setFont(font);
    }


    private void showInfoLine(Graphics2D g2d) {

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        /* Kerning makes the text spacing more natural */
        map.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);

        /* This colour applies just to the font, not other rendering */
        map.put(TextAttribute.FOREGROUND, Color.GRAY);

        Font font = new Font(Font.SERIF, Font.PLAIN, 11);

        font = font.deriveFont(map);
        g2d.setFont(font);

        FontMetrics fm = getFontMetrics(font);
        int infoLineStringWidth = fm.stringWidth(INFO_LINE)

        int infoLine_x_coord = X_CANVAS_SIZE - TRANSLATION_OFFSET - infoLineStringWidth
        int infoLine_y_coord = TRANSLATION_OFFSET - 5

        g2d.drawString(INFO_LINE, infoLine_x_coord, infoLine_y_coord)

    }


    private void drawRaster(Graphics2D g2d) {
        int RASTER = 20

        g2d.setStroke(new BasicStroke(0.5f))
        g2d.setPaint(Color.lightGray)
        Font font = new Font("Serif", Font.PLAIN, 10);
        g2d.setFont(font);



        0.step TRANSLATION_OFFSET, RASTER, { coord ->
            // raster parallel to y-axis
            g2d.draw(new Line2D.Float(coord, -TRANSLATION_OFFSET, coord, TRANSLATION_OFFSET))
            g2d.drawString(coord.toString(), coord, -TRANSLATION_OFFSET + 20)

            // raster parallel to x-axis
            g2d.draw(new Line2D.Float(-TRANSLATION_OFFSET, coord, TRANSLATION_OFFSET, coord))
            g2d.drawString(coord.toString(), -TRANSLATION_OFFSET + 15, coord)

        }

        (-1 * TRANSLATION_OFFSET).step 0, RASTER, { coord ->
            g2d.draw(new Line2D.Float(coord, -TRANSLATION_OFFSET, coord, TRANSLATION_OFFSET))
            g2d.drawString(coord.toString(), coord, -TRANSLATION_OFFSET + 15)

            // raster parallel to x-axis
            g2d.draw(new Line2D.Float(-TRANSLATION_OFFSET, coord, TRANSLATION_OFFSET, coord))
            g2d.drawString(coord.toString(), -TRANSLATION_OFFSET + 15, coord)
        }

        // draw point-of-origin
        g2d.setPaint(Color.darkGray)
        Ellipse2D pointOfOrigin = new Ellipse2D.Float(-3, -3, 6, 6)
        g2d.fill(pointOfOrigin)


    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // prepare the canvas for drawing,
        // translate coord system so that 0/0 is center of circle
        translateCenterToZero(g2d)

        // display project name & URL
        showInfoLine(g2d)


        // the actual line drawing between Pairs
        drawLines(g2d)

        drawLegend(g2d)
        drawSegments(g2d)

        // if in debug or devel mode, draw raster
        if (RUNMODE < RUNMODE.PRODUCTION) {
            drawRaster(g2d)
        }

    }

}

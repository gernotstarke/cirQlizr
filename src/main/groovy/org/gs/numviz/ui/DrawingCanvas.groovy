package org.gs.numviz.ui

import org.gs.numviz.NumVizColor
import org.gs.numviz.NumberVisualizer
import org.gs.numviz.numbers.Pair

import javax.swing.JPanel
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Line2D

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
    private Integer MARGIN       = 20


    // program name and author, URL, shown at bottom of canvas
    private String INFO_LINE

    // entry point to the "domain" - in DDD-terms: AggregateRoot
    private NumberVisualizer nv


    DrawingCanvas(int x_resolution, int y_resolution, String infoLine, NumberVisualizer numberVisualizer ) {
        super()
        initCanvas( x_resolution, y_resolution, infoLine, numberVisualizer )
    }


    private void initCanvas(int xFrameSize, int yFrameSize, String infoLine, NumberVisualizer numberVisualizer) {
        X_CANVAS_SIZE = xFrameSize
        Y_CANVAS_SIZE = yFrameSize - LEGEND_WIDTH

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        TRANSLATION_OFFSET = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE).intdiv(2)

        INFO_LINE = infoLine

        assert numberVisualizer != null
        nv = numberVisualizer

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

            }
        }
    }

    /*
    * draw the lines for all pairs
     */

    private void drawLines(Graphics2D g2d) {
        LOGGER.info("entering drawLines method")
        g2d.setStroke(new BasicStroke(1.0f))

        (0..NR_OF_LINES_TO_DRAW - 1).each { pairIndex ->
            Pair currentPair = NUMBER.getPair(pairIndex)
            drawLineForNumberPair(g2d, pairIndex, currentPair.first, currentPair.second)
        }

    }

    /*
    draw line for a single getPair of numbers
     */

    private void drawLineForNumberPair(Graphics2D g2d, int pairIndex, int fromDigit, int toDigit) {
        g2d.setColor(segment[fromDigit].color)

        int fromDigiNodeIndex = segment[fromDigit].getNextFreeDigiNode()
        int toDigiNodeIndex = segment[toDigit].getNextFreeDigiNode()

        g2d.draw(new Line2D.Double(segment[fromDigit].digiNode[fromDigiNodeIndex],
                segment[toDigit].digiNode[toDigiNodeIndex]))

        segment[fromDigit].advanceToNextAvailableDigiNode()
        segment[toDigit].advanceToNextAvailableDigiNode()

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
            g2d.fillRect((X_CANVAS_SIZE - TRANSLATION_OFFSET) - 60, (Y_CANVAS_SIZE - TRANSLATION_OFFSET - (digit + 1) * 35), 30, 30)

            // show corresponding digit
            g2d.drawString(digit.toString(), (X_CANVAS_SIZE - TRANSLATION_OFFSET - 22), (Y_CANVAS_SIZE - TRANSLATION_OFFSET - digit * 35 - 14))
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
        int infoLineStringWidth = fm.stringWidth( INFO_LINE )

        int infoLine_x_coord = X_CANVAS_SIZE - TRANSLATION_OFFSET - infoLineStringWidth
        int infoLine_y_coord = TRANSLATION_OFFSET + MARGIN - 5

        g2d.drawString(INFO_LINE, infoLine_x_coord, infoLine_y_coord)

    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // prepare the canvas for drawing,
        // translate coord system so that 0/0 is center of circle
        translateCenterToZero(g2d)

        // display project name & URL
        showInfoLine( g2d )

        // the actual line drawing between Pairs
        //drawLines(g2d)

        drawLegend(g2d)
        drawSegments(g2d)

    }

}

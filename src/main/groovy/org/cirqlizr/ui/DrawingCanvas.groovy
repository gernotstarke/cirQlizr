/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Dr. Gernot Starke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cirqlizr.ui

import org.cirqlizr.CircularVisualizer
import org.cirqlizr.configuration.Configuration
import org.cirqlizr.configuration.RunMode
import org.cirqlizr.domain.Connection
import org.cirqlizr.domain.ConnectionNode
import org.cirqlizr.domain.Coordinate2D
import org.cirqlizr.domain.data.Pair

import javax.swing.*
import java.awt.*
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.QuadCurve2D
import java.util.List
import java.util.logging.Logger

class DrawingCanvas extends JPanel {

    // size of drawing canvas in pixel-units
    private Integer X_CANVAS_SIZE
    private Integer Y_CANVAS_SIZE

    // to relocate the point-of-origin from upper-left (which is standard in Java2D)
    // to the center of the circle, we need a translation offset
    private Integer TRANSLATION_OFFSET

    private Configuration configuration


    private Integer LEGEND_WIDTH = 40

    // entry point to the "domain" - in DDD-terms: AggregateRoot
    private CircularVisualizer nv

    // accounting which connectionPoint within which Segment is currently active
    private List<Integer> currentConnectionPointInSegment


    private static final Logger LOGGER = Logger.getLogger(DrawingCanvas.class.getName())


    DrawingCanvas(int x_resolution, int y_resolution, CircularVisualizer numberVisualizer, Configuration config) {
        super()
        this.configuration = config

        initCanvas(x_resolution, y_resolution, numberVisualizer)


    }


    private void initCanvas(int xFrameSize, int yFrameSize, CircularVisualizer numberVisualizer) {
        X_CANVAS_SIZE = xFrameSize
        Y_CANVAS_SIZE = yFrameSize

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        setBackground(configuration.BACKGROUND_COLOR)

        TRANSLATION_OFFSET = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE - configuration.MARGIN).intdiv(2)

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

                if (configuration.RUNMODE < RunMode.PRODUCTION) {
                    // draw dot for all connectionNode-instances
                    drawDotForConnectionNodes(g2d, connectionNode)
                }
            }
        }
    }

    /**
     *
     */
    private void drawDotForConnectionNodes(Graphics2D g2d, List<ConnectionNode> nodes) {
        g2d.setPaint(Color.darkGray)
        nodes.each { node ->
            double xCoord = node.coordinate.x
            double yCoord = node.coordinate.y
            Ellipse2D nodeCircle = new Ellipse2D.Double(xCoord - 3, yCoord - 3, 6, 6)
            g2d.fill(nodeCircle)
        }
    }


    private void drawDotAtCoordinate(Graphics2D g2D, Coordinate2D coord) {
        g2D.setPaint(Color.white)
        Ellipse2D nodeCircle = new Ellipse2D.Double(coord.getX() - 3, coord.getY() - 3, 6, 6)
        g2D.fill(nodeCircle)
    }

    /*
   * draw the lines for all pairs
    */

    private void drawLines(Graphics2D g2d) {
        resetConnectionPoints()

        g2d.setStroke(new BasicStroke(1.3f))

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
    draw line for a single getPair of numeric
     */

    private void drawConnectionForNumberPair(Graphics2D g2d, int fromDigit, int toDigit) {
        g2d.setColor(nv.segment[fromDigit].color)

        int fromConnectionNodeIndex = nv.segment[fromDigit].getNextFreeConnectionNode()
        int toConnectionNodeIndex = nv.segment[toDigit].getNextFreeConnectionNode()

        ConnectionNode fromNode = nv.segment[fromDigit].connectionNode[fromConnectionNodeIndex]
        nv.segment[fromDigit].advanceToNextAvailableConnectionNode()
        ConnectionNode toNode = nv.segment[toDigit].connectionNode[toConnectionNodeIndex]

        Coordinate2D bezierControlPoint = Connection.findBezierControlPoint(toNode.angle, fromNode.angle, 300)

        // QuadCurve arguments: startX, startY, ctrlX, ctrlY, endX, endY)
        g2d.draw(new QuadCurve2D.Double(
                fromNode.coordinate.getX(),
                fromNode.coordinate.getY(),
                bezierControlPoint.getX(),
                bezierControlPoint.getY(),
                toNode.coordinate.getX(),
                toNode.coordinate.getY())
        )

        // show BCP only if configured
        if (configuration.SHOW_BCP) drawDotAtCoordinate(g2d, bezierControlPoint)
        //g2d.draw(new Line2D.Double(
        //        nv.segment[fromDigit].connectionNode[fromConnectionNodeIndex].coordinate.toPoint(),
        //        nv.segment[toDigit].connectionNode[toConnectionNodeIndex].coordinate.toPoint()))


        nv.segment[toDigit].advanceToNextAvailableConnectionNode()

        //LOGGER.info "draw line from $fromDigit (${segment[fromDigit].connectionNode[pairIndex]}"
    }

    /*
    * draw the legend
     */

    private void drawLegend(Graphics2D g2d) {

        // setup some nice font parameters for the legend
        initLegendFont(g2d)

        (0..9).each { digit ->
            // set color scheme
            g2d.setPaint(CirqlizrColor.color[digit])

            // draw filled rectangle
            g2d.fillRect((X_CANVAS_SIZE - TRANSLATION_OFFSET) - 55,
                    (Y_CANVAS_SIZE - TRANSLATION_OFFSET - 2 * configuration.MARGIN - (digit + 1) * 35), 30, 30)

            // show corresponding digit
            g2d.drawString(digit.toString(), (X_CANVAS_SIZE - TRANSLATION_OFFSET - 70),
                    (Y_CANVAS_SIZE - TRANSLATION_OFFSET - 2 * configuration.MARGIN - digit * 35 - 13))

        }

        if (configuration.SHOW_LEGEND_STATISTICS) {
            drawLegendStatistics(g2d)
        }
    }

    private void drawLegendStatistics(Graphics2D g2d ) {
        initLegendStatisticFont(g2d)
        (0..9).each { digit ->
            String connectionsPerSegment = nv.segment[digit].connectionNode.size().toString()
            g2d.drawString( connectionsPerSegment, (X_CANVAS_SIZE - TRANSLATION_OFFSET - 20),
                    (Y_CANVAS_SIZE - TRANSLATION_OFFSET - 2 * configuration.MARGIN - digit * 35 - 13))
        }
    }


    private void initLegendFont(Graphics2D g2d) {
        Font font = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT + Font.BOLD, 16);
        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        // find contrasting color so legend font will always be readable
        Color contrastingColor = CirqlizrColor.findContrastingColorFor(configuration.BACKGROUND_COLOR)

        /* This colour applies just to the font, not other rendering */

        map.put(TextAttribute.FOREGROUND, contrastingColor);

        font = font.deriveFont(map);
        g2d.setFont(font);
    }


    private void initLegendStatisticFont(Graphics2D g2d) {
        Font font = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 9);
        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        // find contrasting color so legend statistic font will always be readable
        Color contrastingColor = CirqlizrColor.findContrastingColorFor(configuration.BACKGROUND_COLOR)

        /* This colour applies just to the font, not other rendering */

        map.put(TextAttribute.FOREGROUND, contrastingColor);

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
        int infoLineStringWidth = fm.stringWidth(configuration.INFO_LINE)

        int infoLine_x_coord = X_CANVAS_SIZE - TRANSLATION_OFFSET - infoLineStringWidth
        int infoLine_y_coord = TRANSLATION_OFFSET - 5

        g2d.drawString(configuration.INFO_LINE, infoLine_x_coord, infoLine_y_coord)

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

        // draw legend, if configured
        if (configuration.SHOW_LEGEND) {
            drawLegend(g2d)
        }


        drawSegments(g2d)

        // if in debug or devel mode, draw raster
        if (configuration.RUNMODE < RunMode.PRODUCTION) {
            drawRaster(g2d)
        }

    }

}

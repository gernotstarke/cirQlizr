package org.gs.numviz

// see end of file for licence information

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel
import java.awt.RenderingHints
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import java.util.logging.Logger

/**
 * Java2D based "drawing arena". Contains Segments and their connections.
 **/

class NumberGrapher extends JPanel {

    final static String SELF_PRAISE = "Number visualizer, https://github.com/gernotstarke/num-viz  "

    // size of drawing canvas
    final Integer X_CANVAS_SIZE
    final Integer Y_CANVAS_SIZE

    // some circle properties
    private Point2D center
    private float radius

    // margin between (invisible) circle and JPanel border
    final Integer MARGIN = 20


    // segments to attach lines to...
    List<Segment> segment

    // some properties of segments
    // what part of a circle does a segment extend
    final Double SEGMENT_EXTEND_ANGLE = Math.toRadians( 30 )
    // what's the distance to the next segment
    final Double SEGMENT_PADDING_ANGLE = Math.toRadians( 3 )



    private static final Logger LOGGER = Logger.getLogger(NumberGrapher.class.getName())

    public NumberGrapher(Integer xFrameSize, Integer yFrameSize) {
        super()
        this.X_CANVAS_SIZE = xFrameSize
        this.Y_CANVAS_SIZE = yFrameSize - 40

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        this.center = center()
        this.radius = radius()

        initSegments()
    }

    /*
     * calculates the position of the center
     */

    private Point2D center() {
        Double min = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE)
        Double minHalf = min / 2

        return new Point2D.Double(minHalf, minHalf)
    }

    /*
    * obvious, isn't it?
     */

    private double radius() {
        return (Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE) / 2) - MARGIN
    }


    private void initSegments() {

        double angleExtend = Math.toRadians( 30d )

                segment = new ArrayList<Segment>(10)
        (0..9).each { thisDigit ->
            segment[thisDigit] = new Segment(
                    digit: thisDigit,
                    centerX: center.x,
                    centerY: center.y,
                    color: NumVizColor.color[thisDigit],
                    radius: this.radius,
                    // TODO: adjust angleStart, so that segment 0 starts at top
                    angleStart: thisDigit * ((SEGMENT_EXTEND_ANGLE + (2 * SEGMENT_PADDING_ANGLE))),
                    angleExtend: SEGMENT_EXTEND_ANGLE)

            segment[thisDigit].setDigiPoint()
        }

    }

    /*
     * draw segments with their circular representation
     */

    private void drawSegments(Graphics2D g2d) {

        //g2d.setPaint(Color.lightGray)
        //g2d.setStroke(new BasicStroke(0.5f))

        // Circle -> specialized Ellipse, coords give
        // upper left corner of enclosing rectangle
        // g2d.draw(new Ellipse2D.Double(MARGIN, MARGIN, 2 * radius, 2 * radius))

        // segments represented by Arc2D.Double or Arc2D.Float instances
        g2d.setStroke(new BasicStroke(12.0f))
        Arc2D arc2D = new Arc2D.Double()

        (0..9).each { digit ->
            segment[digit].with {
                g2d.setPaint(color)
                //LOGGER.info "digit $digit: center.x=${center.x}, center.y=${center.y}"

                arc2D.setArcByCenter(centerX, centerY, radius, Math.toDegrees(angleStart), Math.toDegrees(angleExtend), Arc2D.OPEN)
                g2d.draw(arc2D)

                // debugging digiPoint calculation
                //Line2D line = new Line2D.Double( center.x, center.y)
            }
        }
    }

    /*
    * draw the lines
     */

    private void drawLines(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(1.0f))

        g2d.draw( new Line2D.Double( center, segment[0].digiPoint))
        /*g2d.draw( new Line2D.Double( center, segment[1].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[2].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[3].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[4].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[5].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[6].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[7].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[8].digiPoint))
        g2d.draw( new Line2D.Double( center, segment[9].digiPoint))
*/
        drawSingleLine(g2d, 3, 1)

        drawSingleLine(g2d, 1, 4)

    }

    private void drawSingleLine(Graphics2D g2d, int fromDigit, int toDigit) {
        g2d.setColor(segment[fromDigit].color)
    //    g2d.drawLine(segment[fromDigit].digiPoint.getX(), segment[fromDigit].digiPoint.getY(),
    //            segment[toDigit].digiPoint.getY(), segment[toDigit].digiPoint.getY())

        LOGGER.info "draw line from $fromDigit (${segment[fromDigit].digiPoint}"

        Line2D line = new Line2D.Double( segment[fromDigit].digiPoint, segment[toDigit].digiPoint )
        g2d.draw( line )
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
            g2d.fillRect(X_CANVAS_SIZE - 60, Y_CANVAS_SIZE - (digit + 1) * 35, 30, 30)

            // show corresponding digit
            g2d.drawString(digit.toString(), X_CANVAS_SIZE - 22, Y_CANVAS_SIZE - digit * 35 - 14)
        }
    }


    private void initLegendFont(Graphics2D g2d) {
        Font font = new Font(Font.SERIF, Font.TRUETYPE_FONT, 15);
        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();

        /* This colour applies just to the font, not other rendering */
        map.put(TextAttribute.FOREGROUND, Color.DARK_GRAY);

        font = font.deriveFont(map);
        g2d.setFont(font);
    }


    private void someSelfPraise(Graphics2D g2d) {

        // Graphics2D g2d = (Graphics2D) g;

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
        map.put(TextAttribute.FOREGROUND, Color.BLUE);

        Font font = new Font(Font.SERIF, Font.PLAIN, 11);

        font = font.deriveFont(map);
        g2d.setFont(font);

        FontMetrics fm = getFontMetrics(font);
        int width = fm.stringWidth(SELF_PRAISE);

        g2d.drawString(SELF_PRAISE, Application.X_SIZE - width, Application.Y_SIZE - 25);

    }



    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawSegments(g2d)

        drawLegend(g2d)

        drawLines(g2d)

        someSelfPraise(g2d)

    }

}

/*********************************************************************************
 The MIT License (MIT)

 Copyright (c) 2015 Dr. Gernot Starke

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 *********************************************************************************/

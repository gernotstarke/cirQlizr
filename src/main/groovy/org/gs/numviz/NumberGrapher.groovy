package org.gs.numviz

// see end of file for licence information

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel
import java.awt.Point
import java.awt.RenderingHints
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Line2D
import java.awt.geom.Point2D
import java.util.logging.Logger

/**
 * Java2D based "drawing arena". Contains Segments and their connections.
 *
 * During construction of an instance, the point-of-origin is transformed
 * to the center of the circle!
 **/

class NumberGrapher extends JPanel {

    // how many pairs of digits to show in visualization?
    final private Integer NR_OF_LINES_TO_DRAW


    // size of drawing canvas
    private Integer  X_CANVAS_SIZE
    private Integer  Y_CANVAS_SIZE

    // to relocate the point-of-origin from upper-left (which is standard in Java2D)
    // to the center of the circle, we need a translation factor
    private Integer  TRANSLATION_FACTOR

    private Point2D center

    // some circle properties
    private float radius

    // margin between Segments and JPanel border
    final Integer LEGEND_WIDTH = 40

    // width of legend (to the right of the circle)
    final Integer MARGIN = 20


    // segments to attach lines to...
    List<Segment> segment


    // some properties of segments
    // what part of a circle does a segment extend
    final Double SEGMENT_EXTEND_ANGLE_DEG = 30
    final Double SEGMENT_EXTEND_ANGLE = Math.toRadians( SEGMENT_EXTEND_ANGLE_DEG )
    final Double SEGMENT_PADDING_ANGLE_DEG = 3
    // what's the distance to the next segment
    final Double SEGMENT_PADDING_ANGLE = Math.toRadians( SEGMENT_PADDING_ANGLE_DEG )


    private static final Logger LOGGER = Logger.getLogger(NumberGrapher.class.getName())


    final static String SELF_PRAISE = "Number visualizer, https://github.com/gernotstarke/num-viz  "


    /*
    Constructor contains initialization steps:
    1.) setup canvas (with x and y size, translation-factor
    e
     */
    public NumberGrapher(Integer xFrameSize, Integer yFrameSize, Integer nrOfLinesToDraw) {
        super()
        initCanvas(xFrameSize, yFrameSize)

        this.NR_OF_LINES_TO_DRAW = nrOfLinesToDraw

        initSegments()
    }


    private void initCanvas(int xFrameSize, int yFrameSize) {
        X_CANVAS_SIZE = xFrameSize
        Y_CANVAS_SIZE = yFrameSize - LEGEND_WIDTH

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        TRANSLATION_FACTOR = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE).intdiv(2)

        center = new Point(0, 0)

        this.radius = radius()
    }

    /*
     * translates position of center to 0/0
     */
    private void translateCenterToZero( Graphics2D g2d ) {
                g2d.translate( TRANSLATION_FACTOR, TRANSLATION_FACTOR )
    }


    /*
    * obvious, isn't it?
     */
    private double radius() {
        return (Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE) / 2) - MARGIN
    }


    private void initSegments() {

        segment = new ArrayList<Segment>(10)
        (0..9).each { thisDigit ->
            segment[thisDigit] = new Segment(
                    digit: thisDigit,
                    color: NumVizColor.color[thisDigit],
                    radius: this.radius,
                    // TODO: adjust angleStart, so that segment 0 starts at top
                    angleStart: thisDigit * ((SEGMENT_EXTEND_ANGLE + (2 * SEGMENT_PADDING_ANGLE))),
                    angleExtend: SEGMENT_EXTEND_ANGLE)


            segment[thisDigit].setDigiNodes( NR_OF_LINES_TO_DRAW )
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

                arc2D.setArcByCenter(0,0, radius, Math.toDegrees(angleStart), Math.toDegrees(angleExtend), Arc2D.OPEN)
                g2d.draw(arc2D)

            }
        }
    }

    /*
    * draw the lines
     */

    private void drawLines(Graphics2D g2d) {
        LOGGER.info( "entering drawLines method")
        g2d.setStroke(new BasicStroke(1.0f))

        /*
        g2d.draw( new Line2D.Double( center, segment[0].digiNode))
        g2d.draw( new Line2D.Double( center, segment[1].digiNode))
        g2d.draw( new Line2D.Double( center, segment[2].digiNode))
        g2d.draw( new Line2D.Double( center, segment[3].digiNode))
        g2d.draw( new Line2D.Double( center, segment[4].digiNode))
        g2d.draw( new Line2D.Double( center, segment[5].digiNode))
        g2d.draw( new Line2D.Double( center, segment[6].digiNode))
        g2d.draw( new Line2D.Double( center, segment[7].digiNode))
        g2d.draw( new Line2D.Double( center, segment[8].digiNode))
        g2d.draw( new Line2D.Double( center, segment[9].digiNode))
*/
        (0..NR_OF_LINES_TO_DRAW).each { pairIndex ->
            Pair currentPair = Pi.pair( pairIndex )
            drawLineForNumberPair(g2d, pairIndex, currentPair.first, currentPair.second)
        }

    }


    /*
    draw line for a pair of numbers
     */
    private void drawLineForNumberPair(Graphics2D g2d, int pairIndex, int fromDigit, int toDigit) {
        g2d.setColor(segment[fromDigit].color)

        LOGGER.info "draw line from $fromDigit (${segment[fromDigit].digiNode[pairIndex]}"

        g2d.draw( new Line2D.Double( segment[fromDigit].digiNode[pairIndex], segment[toDigit].digiNode[pairIndex] ) )
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
            g2d.fillRect((X_CANVAS_SIZE - TRANSLATION_FACTOR) - 60, (Y_CANVAS_SIZE - TRANSLATION_FACTOR - (digit + 1) * 35), 30, 30)

            // show corresponding digit
            g2d.drawString(digit.toString(), (X_CANVAS_SIZE - TRANSLATION_FACTOR - 22), (Y_CANVAS_SIZE - TRANSLATION_FACTOR- digit * 35 - 14))
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


    private void prepareCanvas( Graphics2D g2d ) {
        drawLegend(g2d)
        drawSegments(g2d)
        someSelfPraise(g2d)
    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // prepare the canvas for drawing,
        // translate coord system so that 0/0 is center of circle
        translateCenterToZero(g2d)
        prepareCanvas( g2d )

        // the actual line drawing between Pairs
        drawLines(g2d)


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

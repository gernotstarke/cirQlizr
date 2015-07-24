package org.gs.numviz

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel
import java.awt.Point
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.Shape
import java.awt.font.TextAttribute
import java.awt.geom.Arc2D
import java.awt.geom.Ellipse2D


class NumberGrapher extends JPanel {
    final static String SELF_PRAISE = "Number visualizer, https://github.com/gernotstarke/num-viz  "

    // size of drawing canvas
    final Integer X_CANVAS_SIZE
    final Integer Y_CANVAS_SIZE

    private Point center
    private int radius

    // margin between (invisible) circle and JPanel border
    final Integer MARGIN = 20


    public NumberGrapher(Integer xFrameSize, Integer yFrameSize) {
        super()
        this.X_CANVAS_SIZE = xFrameSize
        this.Y_CANVAS_SIZE = yFrameSize - 40

        // crash when dimensions are too small
        assert X_CANVAS_SIZE > 1
        assert Y_CANVAS_SIZE > 1

        this.center = center()
        this.radius = radius()
    }

    /*
     * calculates the position of the center
     */

    private Point center() {
        int min = Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE)
        int minHalf = min.intdiv(2)

        return new Point(minHalf, minHalf)
    }

    private radius() {
        return (Math.min(X_CANVAS_SIZE, Y_CANVAS_SIZE).intdiv(2)) - MARGIN
    }


    private void drawSegments(Graphics2D g2d) {

        g2d.setPaint(Color.lightGray)
        g2d.setStroke(new BasicStroke(0.5f))

        // Circle -> specialized Ellipse, coords give
        // upper left corner of enclosing rectangle
        g2d.draw(new Ellipse2D.Double(MARGIN, MARGIN, 2 * radius, 2 * radius))

        g2d.setStroke(new BasicStroke(12.0f))
        Arc2D arc2D = new Arc2D.Double()

        (0..9).each { digit ->
            g2d.setPaint(NumVizColor.color[digit])
            arc2D.setArcByCenter(center.x, center.y, radius, digit * 36 + 3, 30, Arc2D.OPEN)
            g2d.draw(arc2D)
        }
    }


    private void drawLegend(Graphics2D g2d) {

        // setup some nice font parameters for the legend
        initLegendFont(g2d)


        (0..9).each { digit ->
            // set color scheme
            g2d.setPaint( NumVizColor.color[digit])

            // draw filled rectangle
            g2d.fillRect(X_CANVAS_SIZE - 60, Y_CANVAS_SIZE - (digit + 1) * 35, 30, 30)

            // show corresponding digit
            g2d.drawString( digit.toString(), X_CANVAS_SIZE-22, Y_CANVAS_SIZE-digit*35-14)
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

        drawLegend( g2d )

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

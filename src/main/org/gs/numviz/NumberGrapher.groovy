package org.gs.numviz

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
import java.awt.geom.Ellipse2D


class NumberGrapher extends JPanel {
    final static String SELF_PRAISE = "Number visualizer, https://github.com/gernotstarke/num-viz  "

    // the following are defined in NumberVisualizer
    final Integer X_SIZE
    final Integer Y_SIZE

    private Point center
    private int radius

    // margin between (invisible) circle and JPanel border
    final Integer MARGIN = 10


    public NumberGrapher( Integer xSize, Integer ySize ) {
        super()
        this.X_SIZE = xSize
        this.Y_SIZE = ySize

        // crash when dimensions are too small
        assert X_SIZE > 1
        assert Y_SIZE > 1

        this.center = center()
        this.radius = radius()
    }

    /*
     * calculates the position of the center
     */
    private Point center() {
        int min = Math.min( X_SIZE, Y_SIZE)
        int minHalf = min.intdiv( 2 )

        return new Point( minHalf, minHalf )
    }

    private radius() {
        return (Math.min( X_SIZE, Y_SIZE).intdiv(2)) - MARGIN
    }


    private void drawSegments( Graphics2D g2d ) {


        g2d.setPaint(Color.lightGray)
        g2d.setStroke( new BasicStroke( 0.5f ))

        // Circle -> specialized Ellipse, coords give
        // upper left corner of enclosing rectangle
        g2d.draw( new Ellipse2D.Double( MARGIN, MARGIN, 2*radius, 2*radius ))

        g2d.setStroke( new BasicStroke( 12.0f ))
        Arc2D arc2D = new Arc2D.Double()

        (0..9).each { digit ->
            g2d.setPaint( Color.DARK_GRAY)
            arc2D.setArcByCenter( center.x, center.y, radius, digit*36 + 3, 30, Arc2D.OPEN)

            g2d.draw( arc2D )
        }

    }



    private void someSelfPraise( Graphics2D g2d) {

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

        FontMetrics fm = getFontMetrics( font );
        int width = fm.stringWidth( SELF_PRAISE );

        g2d.drawString(SELF_PRAISE, Application.X_SIZE-width, Application.Y_SIZE-25);

    }



    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawSegments( g2d )

        someSelfPraise(g2d)

    }
}


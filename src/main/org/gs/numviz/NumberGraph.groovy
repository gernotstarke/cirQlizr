package org.gs.numviz

import javax.swing.JLabel
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel
import java.awt.RenderingHints
import java.awt.font.TextAttribute;

class NumberGraph extends JPanel {
    final static String SELF_PRAISE = "Number visualizer, https://github.com/gernotstarke/num-viz  "
    //
    private void someSelfPraise( Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh =
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        Hashtable<TextAttribute, Object> map =
                new Hashtable<TextAttribute, Object>();
        g2d.setRenderingHints(rh);


        /* Kerning makes the text spacing more natural */
        map.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);

        /* This colour applies just to the font, not other rendering */
        map.put(TextAttribute.FOREGROUND, Color.BLUE);

        Font font = new Font(Font.SERIF, Font.PLAIN, 11);

        font = font.deriveFont(map);
        g2d.setFont(font);

        FontMetrics fm = getFontMetrics( font );
        int width = fm.stringWidth( SELF_PRAISE );

        g2d.drawString(SELF_PRAISE, NumberVisualizer.X_SIZE-width, NumberVisualizer.Y_SIZE-25);

    }


    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawLine(30, 30, 200, 30);
        g2d.drawLine(200, 30, 30, 200);
        g2d.drawLine(30, 200, 200, 200);
        g2d.drawLine(200, 200, 30, 30);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        someSelfPraise(g)
        doDrawing(g);

    }
}


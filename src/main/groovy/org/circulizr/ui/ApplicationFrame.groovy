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
package org.circulizr.ui

import org.circulizr.NumberVisualizer
import org.circulizr.configuration.RunMode

import javax.swing.*
import java.awt.*
import java.util.logging.Logger

// see end-of-file for license information

/**
 * The window or frame, containing the actual drawing canvas
 */
class ApplicationFrame extends JFrame {

    // size of drawing canvas in pixel-units
    private Integer X_CANVAS_SIZE
    private Integer Y_CANVAS_SIZE

    private static final Logger LOGGER = Logger.getLogger(ApplicationFrame.class.getName())

    // default: development mode, some debug info
    private final RUNMODE = RunMode.DEVELOP


    public ApplicationFrame(String titleText, String infoLine, int resolution, NumberVisualizer numberVisualizer, RunMode mode) {

        this.X_CANVAS_SIZE = resolution + 100
        this.Y_CANVAS_SIZE = resolution

        this.RUNMODE = mode

        add(new DrawingCanvas(X_CANVAS_SIZE, Y_CANVAS_SIZE, infoLine, numberVisualizer, mode));

        setTitle(titleText);
        setSize(X_CANVAS_SIZE, Y_CANVAS_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false) // resolves issue #9

    }


    public
    static showApplicationFrame(String titleText, String infoLine, int resolution, NumberVisualizer nv, RunMode runmode) {


        if (runmode < RunMode.PRODUCTION) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            double height = screenSize.getHeight();

            LOGGER.info "available screen size: width=$width, height=$height"
        }

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ApplicationFrame applicationFrame =
                        new ApplicationFrame(titleText, infoLine, resolution, nv, runmode);

                applicationFrame.setVisible(true);
            }
        })
    }

}



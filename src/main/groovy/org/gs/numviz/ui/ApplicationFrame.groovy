package org.gs.numviz.ui

import javax.swing.*
import java.awt.*

// see end-of-file for license information

/**
 * The window or frame, containing the actual drawing canvas
 */
class ApplicationFrame extends JFrame {



    public ApplicationFrame(String titleText, String infoLine, int x_canvas_size, int y_canvas_size) {

        add( new DrawingCanvas( x_canvas_size, y_canvas_size, infoLine ));

        setTitle( titleText);
        setSize(x_canvas_size, y_canvas_size);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false) // resolves issue #9

    }




    public static showApplicationFrame( String titleText, String infoLine, int x_canvas_size, int y_canvas_size) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                ApplicationFrame applicationFrame =
                        new ApplicationFrame(titleText, infoLine, x_canvas_size, y_canvas_size);

                applicationFrame.setVisible(true);
            }
        })
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



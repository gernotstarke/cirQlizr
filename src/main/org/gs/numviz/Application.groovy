package org.gs.numviz

import javax.swing.JFrame
import java.awt.EventQueue

// see end-of-file for license information

/**
 * startup class for number visualizations
 *
 */

class Application extends JFrame {

        final static int X_SIZE = 800
        final static int Y_SIZE = 700


        public Application() {
            initUI();
        }


        private void initUI() {

            add(new NumberGrapher( X_SIZE, Y_SIZE ));

            setTitle("Number Visualizer");
            setSize(X_SIZE, Y_SIZE);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setResizable( false ) // resolves issue #9
        }


        public static void main(String[] args) {

            // TODO: add command line parsing for color scheme, number, size

            EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    Application ex = new Application(  );
                    ex.setVisible(true);
                }
            });
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



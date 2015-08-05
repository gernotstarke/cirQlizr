package org.gs.numviz

import org.gs.numviz.numbers.Pi
import org.gs.numviz.numbers.SpecialNumber
import org.gs.numviz.ui.ApplicationFrame


// see end-of-file for license information

/**
 * Startup class for circular number visualizations.
 *
 * Configures both domain and UI
 *
 */

class Application  {

    // the number to visualize
    // resolve #25 (abstract class for special numbers)
    final static SpecialNumber NUMBER = new Pi(NR_OF_CONNECTIONS_TO_SHOW + 1)


    // lines to draw = nr-of-digits + 1
    final static int NR_OF_CONNECTIONS_TO_SHOW = 3



    // window/canvas size
    final static int RESOLUTION = 700

    final static String TITLE_TEXT = "CIRCULIZR - ${NR_OF_CONNECTIONS_TO_SHOW} digits of ${NUMBER.name}"

    final static String INFO_LINE = "Circular Visualizer, https://github.com/gernotstarke/circulizr  "



    public static void main(String[] args) {
        // TODO: add command line parsing for color scheme, number, size

        // construct the domain root
        NumberVisualizer numberVisualizer = new NumberVisualizer( NUMBER, NR_OF_CONNECTIONS_TO_SHOW, RESOLUTION)

        // create and initialize segments and digiNodes
        numberVisualizer.initSegments()


        // UI Window
        ApplicationFrame.showApplicationFrame( TITLE_TEXT, INFO_LINE, RESOLUTION, numberVisualizer)


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



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
package org.cirqlizr

import org.cirqlizr.configuration.Configuration
import org.cirqlizr.configuration.RunMode
import org.cirqlizr.ui.ApplicationFrame



/**
 * Startup class for circular number visualizations.
 *
 * Configures both domain and UI
 *
 */

class Application  {

    final static String configFileName = "./cirQlizr.config"
    static Configuration configuration





    public static void main(String[] args) {
        // TODO: add command line parsing for color scheme, number, size

        // read configuration file (default filename: "cirQlizr.config")
        configuration = new Configuration( configFileName )



        // construct the domain root
        NumberVisualizer numberVisualizer = new NumberVisualizer( configuration )

        // create and initialize segments and connectionNodes
        numberVisualizer.initSegments()

        // generate report only in DEBUG and DEVELOP mode
        if (configuration.RUNMODE < RunMode.PRODUCTION) {
            numberVisualizer.domainReport()
        }


        // UI Window
        ApplicationFrame.showApplicationFrame( configuration, numberVisualizer)


    }
}

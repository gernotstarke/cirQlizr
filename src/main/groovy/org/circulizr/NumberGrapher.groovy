package org.circulizr

import org.circulizr.numbers.SpecialNumber

// see end of file for licence information


import javax.swing.JPanel
import java.util.logging.Logger

/**
 * Java2D based "drawing arena". Contains Segments and their connections.
 *
 * During construction of an instance, the point-of-origin is transformed
 * to the center of the circle!
 **/

class NumberGrapher extends JPanel {

    // what number to show
    final private SpecialNumber NUMBER

    // how many pairs of digits to show in visualization?
    final private Integer NR_OF_LINES_TO_DRAW

    // some circle properties:

    private float radius

    // segments to attach lines to...
    List<Segment> segment

    // some properties of segments
    // what part of a circle does a segment extend
    final Double SEGMENT_EXTEND_ANGLE_DEG = 30
    final Double SEGMENT_EXTEND_ANGLE = Math.toRadians(SEGMENT_EXTEND_ANGLE_DEG)
    final Double SEGMENT_PADDING_ANGLE_DEG = 3
    // what's the distance to the next segment
    final Double SEGMENT_PADDING_ANGLE = Math.toRadians(SEGMENT_PADDING_ANGLE_DEG)


    private static final Logger LOGGER = Logger.getLogger(NumberGrapher.class.getName())



    /*
    Constructor contains initialization steps:
    1.) setup canvas (with x and y size, translation-factor
    e
     */

    public NumberGrapher(SpecialNumber number, Integer nrOfLinesToDraw, Integer xFrameSize, Integer yFrameSize) {
        super()

        this.NR_OF_LINES_TO_DRAW = nrOfLinesToDraw
        this.NUMBER = number

        initCanvas(xFrameSize, yFrameSize)

        initSegments()
    }




    private void initSegments() {

        segment = new ArrayList<Segment>(10)

        (0..9).each { thisDigit ->
            segment[thisDigit] = new Segment(
                    digit: thisDigit,
                    nrOfRequiredDigiNodes: NUMBER.countDigit(thisDigit),
                    color: NumVizColor.color[thisDigit],
                    radius: this.radius,
                    // TODO: adjust angleStart, so that segment 0 starts at top
                    angleStart: thisDigit * ((SEGMENT_EXTEND_ANGLE + (2 * SEGMENT_PADDING_ANGLE))),
                    angleExtend: SEGMENT_EXTEND_ANGLE)

            // create digiNodes (== lineEndings) only if this digit occurs once or more
            if (segment[thisDigit].nrOfRequiredDigiNodes > 0) {
                segment[thisDigit].with {

                    // LOGGER.info "setting up ${nrOfRequiredDigiNodes} digiNodes for Segment[${thisDigit}]: angleStart=${angleStart}, angleExtend=${angleExtend} and radius=${radius}"
                    setUpDigiNodes()
                }
            }

        }

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

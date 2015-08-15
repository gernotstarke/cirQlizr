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
package org.circulizr

import org.circulizr.configuration.Configuration
import org.circulizr.domain.Segment
import org.circulizr.domain.numbers.SpecialNumber
import org.circulizr.ui.CirculizrColor

import java.util.logging.Logger

// see end-of-file for license information

/**
 * Coordinates "business logic" of number visualization.
 */
class NumberVisualizer {

    // what number to show
    final private SpecialNumber NUMBER

    // how many pairs of digits to show in visualization?
    final protected Integer NR_OF_CONNECTIONS_TO_SHOW

    // some circle properties:
    private float radius

    // segments to attach connections to...
    // every segment instance corresponds to one digit
    public List<Segment> segment

    // some properties of (all) segments
    // over what angle does a segment extend
    final Double SEGMENT_EXTEND_ANGLE_DEG = 32
    final Double SEGMENT_EXTEND_ANGLE = Math.toRadians(SEGMENT_EXTEND_ANGLE_DEG)
    final Double SEGMENT_PADDING_ANGLE_DEG = 2
    // what's the distance to the next segment
    final Double SEGMENT_PADDING_ANGLE = Math.toRadians(SEGMENT_PADDING_ANGLE_DEG)


    private static final Logger LOGGER = Logger.getLogger(NumberVisualizer.class.getName())


    public NumberVisualizer( Configuration config) {

        assert config.NUMBER != null : "cannot visualize null value"
        assert config.NR_OF_CONNECTIONS_TO_SHOW >= 0 : "cannot visualize 0 connections"

        // the most important property: what number shall we visualize?
        this.NUMBER = config.NUMBER

        this.NR_OF_CONNECTIONS_TO_SHOW = config.NR_OF_CONNECTIONS_TO_SHOW


        this.radius = (config.OUTPUT_RESOLUTION- 2 * config.MARGIN).intdiv(2)

    }

    /**
     * initialize the segments, including digiNodes
     */
    public void initSegments() {

        segment = new ArrayList<Segment>(10)

        (0..9).each { thisDigit ->
            segment[thisDigit] = new Segment(
                    digit: thisDigit,
                    nrOfRequiredDigiNodes: NUMBER.countOccurencesInPairs(thisDigit, NR_OF_CONNECTIONS_TO_SHOW-1),
                    color: CirculizrColor.color[thisDigit],
                    radius: this.radius,
                    // TODO: adjust angleStart, so that segment 0 starts at top
                    angleStart: thisDigit * ((SEGMENT_EXTEND_ANGLE + (2 * SEGMENT_PADDING_ANGLE))),
                    angleExtend: SEGMENT_EXTEND_ANGLE)

            // create digiNodes (== lineEndings) only if this digit occurs once or more
            if (segment[thisDigit].nrOfRequiredDigiNodes > 0) {
                segment[thisDigit].with {

                    println "setting up ${nrOfRequiredDigiNodes} digiNodes for Segment[${thisDigit}]: angleStart=${angleStart}, angleExtend=${angleExtend} and radius=${radius}"
                    setUpDigiNodes()
                }
            }

        }

    }

    /**
     * output a domain report, might be useful for debugging
     */
    public void domainReport() {

        println "="*80 + "\n"
        println "Circulizr Domain Report for ${NR_OF_CONNECTIONS_TO_SHOW} connections for ${NUMBER.name} (${NUMBER.digits})"

        // print segment information
        segment.each { oneSegment ->
            println oneSegment.toString()
            // print digiNode information
            oneSegment.digiNode.each { thisNode ->
                println "    " + thisNode.toString()
            }
            println "="*80
        }
    }

}

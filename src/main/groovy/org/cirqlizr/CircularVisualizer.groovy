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
import org.cirqlizr.domain.Segment
import org.cirqlizr.domain.data.DataElement
import org.cirqlizr.domain.data.NumericData
import org.cirqlizr.ui.CirqlizrColor

import java.util.logging.Logger

// see end-of-file for license information

/**
 * Coordinates "business logic" of number visualization.
 */
class CircularVisualizer {

    // what number to show
    final private NumericData NUMBER

    // how many pairs of elements to show in visualization?
    final protected Integer NR_OF_CONNECTIONS_TO_SHOW

    // some more configuration stuff
    private Configuration configuration

    // some circle properties:
    private float radius

    // segments to attach connections to...
    // every segment instance corresponds to one element
    public List<Segment> segment

    // some properties of (all) segments
    // over what angle does a segment extend
    final Double SEGMENT_EXTEND_ANGLE_DEG = 32
    final Double SEGMENT_EXTEND_ANGLE = Math.toRadians(SEGMENT_EXTEND_ANGLE_DEG)
    final Double SEGMENT_PADDING_ANGLE_DEG = 2
    // what's the distance to the next segment
    final Double SEGMENT_PADDING_ANGLE = Math.toRadians(SEGMENT_PADDING_ANGLE_DEG)


    private static final Logger LOGGER = Logger.getLogger(CircularVisualizer.class.getName())


    public CircularVisualizer( Configuration config) {

        assert config.NUMBER != null : "cannot visualize null value (likely reason: no data configured)"
        assert config.NR_OF_CONNECTIONS_TO_SHOW >= 0 : "cannot visualize negative nr of connections"

        // the most important property: what number shall we visualize?
        this.NUMBER = config.NUMBER

        this.NR_OF_CONNECTIONS_TO_SHOW = config.NR_OF_CONNECTIONS_TO_SHOW


        this.radius = (config.OUTPUT_RESOLUTION- 2 * config.MARGIN).intdiv(2)

        this.configuration = config
    }


    /**
     * initialize the segments, including connectionNodes
     */
    public void initSegments() {

        segment = new ArrayList<Segment>(10)

        (0..9).each { thisDigit ->
            segment[thisDigit] = new Segment(
                    element: thisDigit,
                    nrOfRequiredConnectionNodes: NUMBER.countOccurencesInPairs(new DataElement( thisDigit), NR_OF_CONNECTIONS_TO_SHOW-1),
                    color: CirqlizrColor.color[thisDigit],
                    radius: this.radius,
                    // TODO: adjust angleStart, so that segment 0 starts at top
                    angleStart: thisDigit * ((SEGMENT_EXTEND_ANGLE + (2 * SEGMENT_PADDING_ANGLE))),
                    angleExtend: SEGMENT_EXTEND_ANGLE)

            // create connectionNodes (== lineEndings) only if this element occurs once or more
            if (segment[thisDigit].nrOfRequiredConnectionNodes > 0) {
                segment[thisDigit].with {

                    if (configuration.RUNMODE < RunMode.PRODUCTION) {
                        LOGGER.info "setting up ${nrOfRequiredConnectionNodes} connectionNodes for Segment[${thisDigit}]: angleStart=${angleStart}, angleExtend=${angleExtend} and radius=${radius}"
                    }
                    setUpConnectionNodes()
                }
            }

        }

    }

    /**
     * output a domain report, might be useful for debugging
     */
    public void domainReport() {

        println "="*80 + "\n"
        println "Circulizr Domain Report for ${NR_OF_CONNECTIONS_TO_SHOW} connections for ${NUMBER.name} (${NUMBER.elements})"

        // print segment information
        segment.each { oneSegment ->
            println oneSegment.toString()
            // print connectionNode information
            oneSegment.connectionNode.each { thisNode ->
                println "    " + thisNode.toString()
            }
            println "="*80
        }
    }

}

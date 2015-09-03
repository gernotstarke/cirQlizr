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

import org.cirqlizr.domain.Segment
import spock.lang.Specification
import static groovy.test.GroovyAssert.shouldFail

// see end-of-file for license information


class SegmentSpec extends Specification {

    double SEGMENT_PADDING_ANGLE

    def setup() {
        SEGMENT_PADDING_ANGLE = Math.toRadians(3)
    }


    def "Correct number of connectionNodes is created for Segment"() {
        given:
        // segment where digit "0" occurs once!
        Segment s = new Segment(
                digit: 0,
                nrOfRequiredConnectionNodes: 1,
                radius: 100,
                angleStart: 0 + (2 * SEGMENT_PADDING_ANGLE),
                angleExtend: Math.toRadians(30))

        when:
        s.setUpConnectionNodes()

        then:
        // exactly ONE connectionNode needs to be created
        s.connectionNode.size() == 1

    }

    /*
    connectionNodes shall be evenly spread across segments
     */
    //@Unroll
    def "actual angles are evenly spread across segment"(int nrOfNodes, List<Double> expectedAngles) {
        given:

        double angleStart = 0
        double angleExtend = Math.toRadians(30)

        Segment s = new Segment(
                digit: 0,
                nrOfRequiredConnectionNodes: nrOfNodes,
                radius: 1,
                angleStart: angleStart,
                angleExtend: angleExtend)

        List<Double> actualAngles = new ArrayList<Double>(nrOfNodes)

        when:
        s.setUpConnectionNodes()

        s.connectionNode.each {
            actualAngles.add(it.angle)
        }

        then:
        //actualAngles == expectedAngles
        actualAngles.size() == expectedAngles.size()
        AssertHelper.assertCloseTo(actualAngles, expectedAngles, 0.1)


        where:
        nrOfNodes | expectedAngles
        1         | [0.26]
        2         | [0.17, 0.35]
        3         | [0.13, 0.26, 0.39]

    }


    /* zero connectionNodes shall fail

     */
    def "zero connectionNodes shall fail"() {
        given:

        double angleStart = 0
        double angleExtend = Math.toRadians(30)

        Segment s = new Segment(
                digit: 0,
                nrOfRequiredConnectionNodes: 0,
                radius: 1,
                angleStart: angleStart,
                angleExtend: angleExtend)


        expect:
        shouldFail(AssertionError) {
            s.setUpConnectionNodes()

        }
    }

    def "nrOfConnections shall return correct value"() {
        given:
           Segment s = new Segment(
                digit: 0,
                nrOfRequiredConnectionNodes: 0)
        expect:
            s.nrOfConnections() == 0
    }


    def "calculate delta angle"(int nrOfNodes, double angleExtend, double deltaAngle) {

        when:
        double actualDelta = Segment.deltaAngle(nrOfNodes, angleExtend)

        then:
        AssertHelper.assertCloseTo(deltaAngle, actualDelta, 0.01)

        where:
        nrOfNodes | angleExtend        | deltaAngle
        0         | 30d                | 15d
        0         | 50d                | 25d
        0         | 90d                | 45d
        0         | Math.PI            | Math.PI / 2
        1         | 30d                | 15d
        1         | 50d                | 25.0
        2         | 30d                | 10.0
        3         | 30d                | 7.5
        10        | 30d                | 2.7272

        100       | Math.toRadians(30) | Math.toRadians(0.297029703)
    }

}

package org.gs.numviz

import org.gs.AssertHelper
import spock.lang.Specification

// see end-of-file for license information


class SegmentSpec extends Specification {

    double SEGMENT_PADDING_ANGLE

    def setup() {
      SEGMENT_PADDING_ANGLE = Math.toRadians( 3 )
    }


    def "Correct number of digiNodes is created for Segment"() {
        given:
        // segment where digit "0" occurs once!
        Segment s = new Segment(
                digit: 0,
                nrOfRequiredDigiNodes: 1,
                radius: 100,
                angleStart: 0 + (2 * SEGMENT_PADDING_ANGLE),
                angleExtend: Math.toRadians(30))

        when:
        s.setUpDigiNodes()

        then:
            // exactly ONE digiNode needs to be created
            s.digiNode.size() == 1

    }


    /*
    digiNodes shall be evenly spread across segments
     */
    //@Unroll
    def "actual angles are evenly spread across segment"(int nrOfNodes, List<Double> expectedAngles) {
        given:

        double angleStart = 0
        double angleExtend = Math.toRadians(30)

        Segment s = new Segment(
                digit: 0,
                nrOfRequiredDigiNodes: nrOfNodes,
                radius: 1,
                angleStart: angleStart,
                angleExtend: angleExtend)

        List<Double> actualAngles = new ArrayList<Double>(nrOfNodes)

        when:
            s.setUpDigiNodes()

        s.digiNode.each {
            actualAngles.add( it.angle )
        }

        then:
            //actualAngles == expectedAngles
            actualAngles.size() == expectedAngles.size()
            AssertHelper.assertCloseTo(actualAngles, expectedAngles, 0.1)


        where:
        nrOfNodes | expectedAngles
        1         | [0.26]
        2         | [0.17, 0.35]
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

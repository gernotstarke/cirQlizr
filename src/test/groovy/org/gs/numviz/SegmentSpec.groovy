package org.gs.numviz

import org.gs.AssertHelper
import spock.lang.Specification

// see end-of-file for license information


class SegmentSpec extends Specification {

    double SEGMENT_PADDING_ANGLE

    def setup() {
      SEGMENT_PADDING_ANGLE = Math.toRadians( 3 )
    }


    def "DigiNodes are distributed evenly along Segment Zero"() {
        given:
        Segment s = new Segment(
                digit: 0,
                color: NumVizColor.color[0],
                radius: 100,
                angleStart: 0 + (2 * SEGMENT_PADDING_ANGLE),
                angleExtend: 30)

        when:
        s.setDigiNodesCoordinates(1)

        then:
            s.digiNode.size() == 1

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

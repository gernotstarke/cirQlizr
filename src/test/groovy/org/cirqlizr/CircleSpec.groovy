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

import org.cirqlizr.domain.Circle
import org.cirqlizr.domain.Coordinate2D
import spock.lang.Specification

// see end-of-file for license information


/*
 *
 */
class CircleSpec extends Specification {

    def "delta angle calculation"(double alpha, double beta, double expectedDelta, double epsilon) {

        expect:
        org.junit.Assert.assertEquals( "expect delta of $alpha and $beta to be $expectedDelta",
                    expectedDelta, Circle.deltaAngle( alpha, beta), epsilon )

        where:
        alpha | beta | expectedDelta | epsilon
        0     |    0 |           0   |   0.001
        0     |    3 |           3   |   0.05
        0     | 2*Math.PI |      0   |   0.05
        0     |  0.5 |           0.5 |   0.001
        1.5   |  4.6 |           3.1 |   0.01
    }



    def "Circle crosses X axis With Angle Zero at Zero"() {
        given:
        Circle circle = new Circle(center: new Coordinate2D(0, 0), radius: 1)

        when:
        Coordinate2D onXAxis = circle.getPointByAngle(0)

        Double actualX = onXAxis.getX()
        Double actualY = onXAxis.getY()

        then:
        AssertHelper.assertCloseTo(actualX, 1, 0.02)
        AssertHelper.assertCloseTo(actualY, 0, 0.02)
    }

    /*
    * with angles 0, 90, 180, 270 the circle shall cross the x and y axis
     */

    def "Circle crosses Axis"(double angle, double xAxis, double yAxis) {
        given:
        Circle circle = new Circle(center: new Coordinate2D(0, 0), radius: 1)

        when:
        Coordinate2D crossingAxis = circle.getPointByAngle(angle)

        then:
        AssertHelper.assertCloseTo( (float) crossingAxis.getX(), xAxis, 0.01f )
        AssertHelper.assertCloseTo( (float) crossingAxis.getY(), yAxis, 0.01f )

        where:
        angle        | xAxis | yAxis
        0            |  1    |  0
        1.570796327  |  0    |  1  // 90 DEG
        3.141592655  | -1    |  0  // 180 DEG
        4.712388980  |  0    | -1  // 270 DEG
    }


    def "Circle Crossing at Fourty Five Degrees"() {
        given:
        Circle circle = new Circle(center: new Coordinate2D(0, 0), radius: 1)

        when:
        Coordinate2D onXAxis = circle.getPointByAngle( Math.toRadians( 45 ))

        then:
        AssertHelper.assertCloseTo( 0.7071067812f, (Float) onXAxis.getX(), 0.01f )
        AssertHelper.assertCloseTo( 0.7071067812f, (Float) onXAxis.getY(), 0.01f)
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



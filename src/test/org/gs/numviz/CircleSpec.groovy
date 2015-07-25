package org.gs.numviz

import spock.lang.Specification
import static spock.util.matcher.HamcrestMatchers.closeTo

import java.awt.Point

// see end-of-file for license information


class CircleSpec extends Specification {

    def "Circle crosses X axis With Angle Zero at Zero"() {
        given:
        Circle circle = new Circle(center: new Point(0, 0), radius: 1)

        when:
        Point onXAxis = circle.getPointByAngle(0)

        Double actualX = onXAxis.getX()
        Double actualY = onXAxis.getY()

        then:
        closeTo(actualX, 1)
        closeTo(actualY, 0)
    }

    /*
    * with angles 0, 90, 180, 270 the circle shall cross the x and y axis
     */

    def "Circle crosses Axis"(int angle, int xAxis, int yAxis) {
        given:
        Circle circle = new Circle(center: new Point(0, 0), radius: 1)

        when:
        Point crossingAxis = circle.getPointByAngle(angle)

        then:
        closeTo(crossingAxis.getX(), xAxis)
        closeTo(crossingAxis.getY(), yAxis)

        where:
        angle| xAxis | yAxis
        0    | 1    | 0
        90   | 0    | 1
        180  | -1   | 0
        270  | 0    | -1
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



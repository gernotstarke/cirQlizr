package org.gs.numviz

import spock.lang.Specification

import java.awt.geom.Point2D

import static spock.util.matcher.HamcrestMatchers.closeTo

import java.awt.Point

// see end-of-file for license information


class CircleSpec extends Specification {

    def "Circle crosses X axis With Angle Zero at Zero"() {
        given:
        Circle circle = new Circle(center: new Point(0, 0), radius: 1)

        when:
        Point2D onXAxis = circle.getPointByAngle(0)

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
        Point2D crossingAxis = circle.getPointByAngle(angle)

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


    def "Circle Crossing at Fourty Five Degrees"() {
        given:
        Circle circle = new Circle(center: new Point2D.Double(0, 0), radius: 20)

        when:
        Point2D onXAxis = circle.getPointByAngle(45)

        then:
        assertCloseTo( 10.506f, (Float) onXAxis.getX(), 0.01f )
        assertCloseTo( 17.018f, (Float) onXAxis.getY(), 0.01f) // 20 * sin(45) = 20 * 0.8509
    }


    /*
    * the Java2D coordinate space has 0/0 in the upper left corner and extends downwards...
    * (counter-intuitive)
     */
    def "Center in 2D Geometry Coordinates"() {

    }

    /*
    * helper method to assert-with-epsilon
     */
    def assertCloseTo( float expected, float actual, float epsilon) {
        return Math.abs( Math.abs( expected ) - Math.abs( actual)) < epsilon
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



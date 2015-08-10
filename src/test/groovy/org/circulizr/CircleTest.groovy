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

import org.junit.Before
import org.junit.Test

// see end-of-file for license information


class CircleTest extends GroovyTestCase {

    Circle circle

    @Before
    public void setUp() {
        circle = new Circle( center: new Coordinate2D(0,0), radius: 1)
    }

    /**
    * on circle with radius 1 and center 0,0, the point x=1, y=0 should
     * be on the circle!
     */
    @Test
    public void testAngleZeroOnXAxis() {
        circle = new Circle( center: new Coordinate2D(0,0), radius: 1)

        Coordinate2D onXAxis = circle.getPointByAngle( 0 )

        Double actualX = onXAxis.getX()
        Double actualY = onXAxis.getY()
        Double epsilon = 0.01

        assertEquals( 1, actualX, epsilon )
        assertEquals( 0, actualY, epsilon )
    }
}

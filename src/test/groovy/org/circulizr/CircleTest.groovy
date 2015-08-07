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

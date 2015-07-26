package org.gs.numviz

import java.awt.Point
import java.awt.geom.Point2D

// see end-of-file for license information


class Circle {
    private Point2D center
    private int radius

    /**
     * Determine coordinates of a point on the circle
     * as a function of its center coords, the radius
     * and the angle.
     * Result derived by the <a href="href="http://www.mathopenref.com/coordparamcircle.html">
     *     parametric circle equation</a>
     * @param angle
     * @return
     */
    public Point2D getPointByAngle( Float angle) {
        Double x = radius * Math.cos( angle ) + center.getX()
        Double y = radius * Math.sin( angle ) + center.getY()
        return new Point2D.Double( x , y )

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



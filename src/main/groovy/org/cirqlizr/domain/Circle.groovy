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
package org.cirqlizr.domain
// see end-of-file for license information


class Circle {
    private Coordinate2D center
    private double radius

    /**
     * determine points on this circle instance
     * @param angle
     * @return
     */
    public Coordinate2D getPointByAngle( double angle) {
        Double x = radius * Math.cos( angle ) + center.getX()
        Double y = radius * Math.sin( angle ) + center.getY()
        return new Coordinate2D( x , y )
    }

    /**
     * Determine coordinates of a point on the circle
     * as a function of its center coords, the radius
     * and the angle.
     * Result derived by the <a href="href="http://www.mathopenref.com/coordparamcircle.html">
     *     parametric circle equation</a>
     * @param angle
     * @return
     */
    public static Coordinate2D getPointByCenterRadiusAngle( Coordinate2D center, double radius, double angle ) {
        Double x = radius * Math.cos( angle ) + center.getX()
        Double y = radius * Math.sin( angle ) + center.getY()
        return new Coordinate2D( x , y )
    }

    /**
     * gets a point off the point-of-origin with angle and radius
     * @param radius
     * @param angle
     * @return
     */
    public static Coordinate2D getPointByRadiusAngle( double radius, double angle) {
        Double x = radius * Math.cos( angle )
        Double y = radius * Math.sin( angle )
        return new Coordinate2D( x , 1 * y )
    }

    /**
     * finds the delta of two angles (in RAD).
     * Both angles are "normalized" before processing, that means they are taken
     * modulo 2PI
     */
    public static Double deltaAngle( double originalAlpha, double originalBeta) {
        double alpha = originalAlpha % (2 * Math.PI)
        double beta  = originalBeta  % (2 * Math.PI)
        double minAngle = Math.min( alpha, beta)
        double maxAngle = Math.max( alpha, beta)
        return  Math.abs( minAngle - maxAngle)
    }
}


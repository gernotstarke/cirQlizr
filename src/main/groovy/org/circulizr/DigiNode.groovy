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

/**
 * DigiNode represents an endpoint for a visual connection (e.g. line or curve).
 *
 */

class DigiNode {

    Coordinate2D coordinate

    double angle
    double radius

    /**
     * completely create a digiNode instance with its coordinates
     * @param angle
     * @param radius
     */
    public DigiNode( double angle, double radius) {
        this.angle = angle
        this.radius = radius

        this.coordinate = calcCoordinate( angle, radius)

    }


    public Coordinate2D calcCoordinate( double angle, double radius) {

        return Circle.getPointByCenterRadiusAngle( new Coordinate2D(0,0), radius, angle )

    }


    public String toString() {
        return """radius = $radius, angle=${sprintf("%3.1f (%3.1f°)", angle, Math.toDegrees(angle))}, coord=(${sprintf("%3.1f", coordinate.x)}, ${sprintf("%3.1f", coordinate.y)}})
"""
    }

}

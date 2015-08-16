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

import java.awt.geom.Point2D


/**
 * represents a point within a 2-dimensional coordinate system.
 * In reality will be subclassed by something like
 * @see java.awt.Point2D or @see javafx.geomentry.Point2D
 */
class Coordinate2D {

    private Point2D point


    public Coordinate2D(Number x, Number y) {
        assert (x != null) && (y != null): "parameters must not be null, x=${x}, y=${y}"
        this.point = new Point2D.Double(x, y)

    }


    public double getX() {
        return point.getX()
    }

    public double getY() {
        return point.getY()
    }

    public Point2D toPoint() {
        return point
    }
    /**
     * Mirrors a coord at x-axis by inverting its y value
     * @param origin
     * @return
     */
    public Coordinate2D mirrorAtXAxis(Coordinate2D origin) {
        // as Java coordinate system really sucks - we need to invert the Y value
        return new Coordinate2D(origin.getX(), -1 * origin.getY()())
    }

    /**
     * mirror itself at x-axis
     */
    public void mirrorAtXAxis() {
        point.setLocation( this.getX(), -1 * this.getY())
    }

}


package org.gs.numviz

import java.awt.Color
import java.awt.Point
import java.awt.geom.Point2D
import java.util.logging.Logger


/**
 * Segment represents a digit and is drawn as part of a circle.
 * When we use 10 Segments, every instance extends 36 deg, that is 0.2*&#960;rad.
 *
 */

class Segment {

    private int digit

    private double radius

    private Color color

    private double angleStart
    private double angleExtend

    public Point2D digiPoint

    private static final Logger LOGGER = Logger.getLogger(Segment.class.getName())


    // implicit constructor to allow named parameters

    /**
     * calculate the DigiPoint for this Segment with the
     * <a href="http://www.mathopenref.com/coordparamcircle.html">parametric circle equation</a>:
     * x = radius * cos(t)    y = radius * sin(t) with t being the angle...
     * @return point in Segment, where lines will be attached
     */
    public void setDigiPoint() {
        // TODO: evenly distribute digiPoints along the segment
        double angle = angleStart + angleExtend/2


        digiPoint = Circle.getPointByCenterRadiusAngle( new Point(0,0), radius, angle )

        LOGGER.info "Segment[${digit}]: digiPoint: $digiPoint, angle=$angle, radius=$radius"
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

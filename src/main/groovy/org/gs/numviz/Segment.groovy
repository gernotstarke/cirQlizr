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

    // several "connection points" for lines
    public List<Point2D> digiNode

    private static final Logger LOGGER = Logger.getLogger(Segment.class.getName())


    // implicit constructor to allow named parameters


    /**
     * calculate the digiNodes for this Segment with the
     * <a href="http://www.mathopenref.com/coordparamcircle.html">parametric circle equation</a>:
     * x = radius * cos(t)    y = radius * sin(t) with t being the angle...
     * @return points in Segment, where lines will be attached
     */
    public void setDigiNodesCoordinates(int nrOfDigitsToShow ) {

        // we don't support 0 digits
        assert nrOfDigitsToShow > 0

        digiNode = new ArrayList<Point2D.Double>( nrOfDigitsToShow)

        double deltaAngle = deltaAngle( nrOfDigitsToShow, angleExtend)

        // for each digit to show, create one digiNode
        (0..(nrOfDigitsToShow)).each { nrOfCurrentDigiNode ->
           createDigiNode( nrOfCurrentDigiNode, angleStart, deltaAngle)
        }
    }

    private Point2D createDigiNode( int nrOfCurrentDigiNode, double angleStart, double deltaAngle) {

        double angle = angleStart + deltaAngle * nrOfCurrentDigiNode

        digiNode[nrOfCurrentDigiNode] = Circle.getPointByCenterRadiusAngle( new Point(0,0), radius, angle )

        // as Java coordinate system really sucks - we need to invert the Y value
        // (thx to Groovy for awesome "with" statement)
        digiNode[nrOfCurrentDigiNode].with {
            setLocation( getX(), -1 * getY())
        }

        // logging was only required in initial dev phase
        // LOGGER.info "Segment[${digit}]: angle=$angleStart, radius=$radius"
    }

    /**
     * what is the delta-angle between digiNodes?
     * @param nrOfDigiNodes
     * @param angleExtend
     * @return
     */
    public static double deltaAngle( int nrOfDigiNodes, double angleExtend) {
        assert nrOfDigiNodes >= 0
        assert angleExtend >= 0
        return angleExtend / (Math.max(nrOfDigiNodes, 1) + 1)
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

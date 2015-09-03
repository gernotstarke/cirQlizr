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

import java.awt.Color
import java.util.logging.Logger


/**
 * Segment represents a digit and is drawn as part of a circle.
 * When we use 10 Segments, every instance extends 36 deg, that is 0.2*&#960;rad.
 *
 */

class Segment {

    private int digit

    // how often does this digit occur in pairs?
    // how many connections will start or end in this segment?
    private int nrOfRequiredConnectionNodes

    // what is the number of the next free connectionNode
    private int nextFreeConnectionNode = -1

    private double radius

    private Color color

    private double angleStart
    private double angleExtend

    // several "connection points" for lines
    public List<ConnectionNode> connectionNode

    // a Bezier Control Point
    public Coordinate2D bezierControlPoint

    private static final Logger LOGGER = Logger.getLogger(Segment.class.getName())

    // implicit constructor to allow named parameters

    /**
     * returns the next free connectionNode
     **/
    public int getNextFreeConnectionNode() {

        assert nextFreeConnectionNode <= connectionNode.size(): "Segment $digit has no free connectionNodes after $nextFreeConnectionNode"
        return nextFreeConnectionNode
    }

    /**
     * increments the pointer to next available connectionNode
     */
    public void advanceToNextAvailableConnectionNode() {

        assert nextFreeConnectionNode < connectionNode.size(): "cannot advance connectionNode pointer, as segment $digit has no free connectionNodes after $nextFreeConnectionNode"

        nextFreeConnectionNode += 1
    }

    /**
     * calculate the connectionNodes for this Segment with the
     * <a href="http://www.mathopenref.com/coordparamcircle.html">parametric circle equation</a>:
     * x = radius * cos(t)    y = radius * sin(t) with t being the angle...
     * @return points in Segment, where lines will be attached
     */
    public void setUpConnectionNodes() {

        // digit does not occur in number -> no connectionNodes
        assert this.nrOfRequiredConnectionNodes > 0: "setUpConnectionNodes error: Segment[${this.digit}] cannot create $nrOfRequiredConnectionNodes connectionNodes"

        // as Lists start at index 0:
        connectionNode = new ArrayList<ConnectionNode>(nrOfRequiredConnectionNodes)

        // where to start attaching connections
        nextFreeConnectionNode = 0

        double deltaAngle = deltaAngle(this.nrOfRequiredConnectionNodes, angleExtend)

        // println "will create ${nrOfRequiredConnectionNodes} connectionNodes with deltaAngle=${deltaAngle} for angleExtend=${angleExtend} and angleStart=${angleStart}"

        // for each digit to show, create one connectionNode
        (1..nrOfRequiredConnectionNodes).each { nrOfCurrentConnectionNode ->
            // connectionNode constructor is responsible for calculating Coordinates/Points
            ConnectionNode tmpNode = new ConnectionNode(angleForThisConnectionNode(angleStart, deltaAngle, nrOfCurrentConnectionNode),
                    radius)
            tmpNode.coordinate.mirrorAtXAxis()

            connectionNode[nrOfCurrentConnectionNode - 1] = tmpNode


        }

    }

    /**
     * How many connections does this segment have?
     * @return
     */
    public int nrOfConnections() {
        return nrOfRequiredConnectionNodes
    }


    /**
     * what is the actual angle for this connectionNode?
     */
    public static double angleForThisConnectionNode(double angleStart, double deltaAngle, int nrOfCurrentConnectionNode) {

        double theAngle = angleStart + deltaAngle * (nrOfCurrentConnectionNode)
        return theAngle
    }

    /**
     * what is the delta-angle between connectionNodes? Does NOT depend on starting angle,
     * only on angleExtend and nrOfConnectionNodes
     * @param nrOfConnectionNodes
     * @param angleExtend
     * @return delta-angle between connectionNodes within this segment
     * for examples, {@link SegmentSpec # "ConnectionNodes are distributed evenly along Segment Zero"}:
     *
     */
    public static double deltaAngle(int nrOfConnectionNodes, double angleExtend) {
        assert nrOfConnectionNodes >= 0
        assert angleExtend >= 0
        return angleExtend / (Math.max(nrOfConnectionNodes, 1) + 1)
    }


    /**
     * returns a readable version of this segment
     */
    public String toString() {
        return """Segment $digit requires $nrOfRequiredConnectionNodes connections
      angleStart=${sprintf("%3.3fRAD (%3.3f°)", angleStart, Math.toDegrees(angleStart))}, extend=${sprintf("%3.3fRAD (%3.3f°)", angleExtend, Math.toDegrees(angleExtend))}
"""
    }
}


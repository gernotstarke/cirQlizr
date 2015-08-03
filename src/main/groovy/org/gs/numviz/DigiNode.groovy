package org.gs.numviz

import java.awt.Point
import java.awt.geom.Point2D

// see end-of-file for license information

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
        this.angle = 0
        this.radius = radius

        this.coordinate = calcCoordinate( angle, radius)

    }


    public Coordinate2D calcCoordinate( double angle, double radius) {

        return Circle.getPointByCenterRadiusAngle( new Coordinate2D(0,0), radius, angle )

    }



}

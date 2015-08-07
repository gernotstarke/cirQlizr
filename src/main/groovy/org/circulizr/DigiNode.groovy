package org.circulizr
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

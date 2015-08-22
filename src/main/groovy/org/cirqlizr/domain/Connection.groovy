package org.cirqlizr.domain

// see end-of-file for license information


class Connection {
    final ConnectionStyle style

    /**
     * ConnectionNode instead of Coordinate2D,
     * because we need more informations about
     * a point than just the coordinates
     */
    final ConnectionNode startPoint

    final ConnectionNode endPoint

    Coordinate2D bezierControlPoint


    /*
   * calculates a control point between two angles
    */
    public static Coordinate2D findBezierControlPoint( double alpha, double beta, double radius) {

        double minAngle = Math.min( alpha, beta)
        double deltaAngle = Circle.deltaAngle( alpha, beta )

        double midAngle = (minAngle + deltaAngle / 2) % (2 * Math.PI)

        double multiplier = Math.abs( 0.5 * Math.sin( deltaAngle / 2 ))

        return Circle.getPointByRadiusAngle( radius * multiplier, midAngle)
    }
}

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

}

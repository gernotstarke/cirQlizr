package org.gs.numviz

import java.awt.Color
import java.awt.geom.Point2D

// see end-of-file for license information

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


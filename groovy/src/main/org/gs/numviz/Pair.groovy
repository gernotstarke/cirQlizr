package org.gs.numviz

import groovy.transform.EqualsAndHashCode

// see end-of-file for license information

@EqualsAndHashCode
class Pair {
    int first
    int second

    public Pair( int first, int second) {
        this.first = first
        this.second = second
    }

    public String toString() {
        return "("+first + ", " + second + ")"
    }

}

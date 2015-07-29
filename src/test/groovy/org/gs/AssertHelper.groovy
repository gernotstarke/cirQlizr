package org.gs
// see end-of-file for license information


class AssertHelper {

    /*
  * helper method to assert-with-epsilon
   */
    public static boolean assertCloseTo( expected, actual, float epsilon) {
        return Math.abs( Math.abs( expected ) - Math.abs( actual)) < epsilon
    }
}

package org.circulizr
// see end-of-file for license information


class AssertHelper {

    /**
     * helper method to assert-with-epsilon
     */
    public static boolean assertCloseTo(expected, actual, float epsilon) {
        assert epsilon>0 : "epsilon must be > 0, was $epsilon"

        return Math.abs(Math.abs(expected) - Math.abs(actual)) <= epsilon
    }


    /**
     * helper method to assertEquals with epsilon to every element of two lists
     */
    public static boolean assertCloseTo(List<Number> actual, List<Number> expected, float epsilon) {
        if (actual == [] && expected == []) return true

        if (actual.size() == expected.size()) {
            return assertCloseTo(actual?.first(), expected?.first(), epsilon) &&
                    assertCloseTo(actual?.tail(), expected?.tail(), epsilon)
        }
        // size does not match -> error
        else return false
    }
}

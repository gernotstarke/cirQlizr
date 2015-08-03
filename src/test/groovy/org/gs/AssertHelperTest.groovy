package org.gs

import spock.lang.Specification
import spock.lang.Unroll

// see end-of-file for license information


class AssertHelperTest extends Specification {


    def "AssertCloseTo"( one, another, float epsilon, boolean isCloseTo) {
        expect:
            AssertHelper.assertCloseTo( one, another, epsilon) == isCloseTo


        where:

        one | another | epsilon  | isCloseTo
        1   | 2       | 0.1f     | false
        1   | 2       | 1.0f     | true
        10  | 20      | 10f      | true


        1   | 1       | 0.1f     | true
        1   | 1       | 0.01f    | true
        1   | 1       | 0.00001f | true

        1.1 | 1.11    | 0.5      | true
        1.1 | 1.11    | 0.1      | true
        1.1 | 1.11    | 0.02     | true
        1.1 | 1.11    | 0.005    | false



    }


    def "Assert Close To for Lists"(List<Double> actual, List<Double> expected, boolean closeTo) {

        expect:
            AssertHelper.assertCloseTo(actual, expected, 0.01) == closeTo

        where:
        actual           | expected | closeTo
        []               | []       | true // base case, empty lists
        [1.0]            | [1.0d]   | true
        []               | []       | true
        [1, 2]           | [1]      | false
        [1.1, 1.2, 1.21] | [1.1, 1.2, 1.21] | true
        [1.1, 1.2, 2.0]  | [1.1, 1.2, 2.1]   | false

        // length of lists differ
        [1]              | [1, 2]            | false


    }
}

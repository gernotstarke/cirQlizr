package org.gs

import spock.lang.Specification

// see end-of-file for license information


class AssertHelperTest extends Specification {
    def "AssertCloseTo"() {

    }

    def "Assert Close To for Lists"(List<Double> actual, List<Double> expected, boolean closeTo) {

        expect:
            AssertHelper.assertCloseTo(actual, expected, 0.1) == closeTo

        where:
        actual           | expected | closeTo
        [1.0]            | [1.0d]   | true
        []               | []       | true
        [1, 2]           | [1]      | false
        [1.1, 1.2, 1.21] | [1.1, 1.2, 1.21] | true

    }
}

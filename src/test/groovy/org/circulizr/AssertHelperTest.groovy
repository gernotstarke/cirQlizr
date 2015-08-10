/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Dr. Gernot Starke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.circulizr

import spock.lang.Specification

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

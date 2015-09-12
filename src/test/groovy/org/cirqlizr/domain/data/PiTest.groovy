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
package org.cirqlizr.domain.data

import org.junit.Before
import org.junit.Test

// see end-of-file for license information


class PiTest extends GroovyTestCase {

    NumericData pi

    @Before
    public void setUp() {
        pi = new Pi(5)
    }

    @Test
    public void testFirstDigitIsThree() {
        GroovyTestCase.assertEquals( "first element must be three", new DataElement(3), pi.getElementAtIndex(0))
    }


    @Test
    public void testIllegalIndex() {
        shouldFail( IndexOutOfBoundsException ) {
            pi.getElementAtIndex(-1)
        }
    }

    @Test
    public void testFirstPairIsThreeOne() {
        Pair p = pi.getPair( 0 )

        assertEquals "Pi's first getPair should be 3 and 1",
                new Pair(3,1), p
    }

    @Test
    public void testSecondPairIsOneFour() {
        Pair p = pi.getPair( 1 )

        assertEquals "Pi's first getPair should be 3 and 1",
                new Pair(1,4), p
    }

    @Test
    public void testLowPrecision() {
        NumericData sn = new Pi(1)

        List<Integer> actual = sn.elements

        assertEquals( "expect Pi with precision==1 to be [3]", [new DataElement(3)], actual)
    }

    @Test
    public void testHighPrecision() {
        NumericData sn = new Pi(1500)

        int actualNrOfDigits = sn.elements.size()

        assertEquals("Pi(1500) should have 1500 digits", 1500, actualNrOfDigits)
    }
}

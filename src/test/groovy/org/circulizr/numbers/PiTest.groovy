package org.circulizr.numbers

import org.junit.Before
import org.junit.Test

// see end-of-file for license information


class PiTest extends GroovyTestCase {

    SpecialNumber pi

    @Before
    public void setUp() {
        pi = new Pi(5)
    }

    @Test
    public void testFirstDigitIsThree() {
        GroovyTestCase.assertEquals( "first digit must be three", 3, pi.getDigit(0))
    }


    @Test
    public void testIllegalIndex() {
        shouldFail( IndexOutOfBoundsException ) {
            pi.getDigit(-1)
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
        SpecialNumber sn = new Pi(1)

        List<Integer> actual = sn.digits

        assertEquals( "expect Pi with precision==1 to be [3]", [3], actual)
    }

    @Test
    public void testHighPrecision() {
        SpecialNumber sn = new Pi(1500)

        int actualNrOfDigits = sn.digits.size()

        assertEquals("Pi(1500) should have 1500 digits", 1500, actualNrOfDigits)
    }
}

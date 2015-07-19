package org.gs.numviz

import org.junit.Before
import org.junit.Test

// see end-of-file for license information


class PiTest extends GroovyTestCase {


    @Test
    public void testFirstDigitIsThree() {
        assertEquals( "first digit must be three", 3, Pi.getDigit(0))
    }


    @Test
    public void testIllegalIndex() {
        shouldFail( IndexOutOfBoundsException ) {
            Pi.getDigit(-1)
        }
    }

    @Test
    public void testFirstPairIsThreeOne() {
        Pair p = Pi.getPair( 0 )

        assertEquals "Pi's first pair should be 3 and 1",
                new Pair(3,1), p
    }

    @Test
    public void testSecondPairIsOneFour() {
        Pair p = Pi.getPair( 1 )

        assertEquals "Pi's first pair should be 3 and 1",
                new Pair(1,4), p
    }


}

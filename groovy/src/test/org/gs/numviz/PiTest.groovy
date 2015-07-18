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
        false
    }
}

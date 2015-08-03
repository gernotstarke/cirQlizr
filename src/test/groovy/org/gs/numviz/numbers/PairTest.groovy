package org.gs.numviz.numbers

import org.gs.numviz.numbers.Pair
import org.gs.numviz.numbers.Pi

// see end-of-file for license information


class PairTest extends GroovyTestCase {

    public void testEqualPair() {
        def p1 = new Pair(3,1)
        def p2 = new Pair(3,1)

        assertEquals( "$p1 and $p2 should be equal", p1, p2)
    }


    public void testGetPair() {
        def pi = new Pi(5)
        assertEquals( "pair 0 should be 3,1", pi.getPair(0), new Pair( 3,1) )
        assertEquals( "pair 1 should be 1,4", pi.getPair(1), new Pair( 1,4) )


    }
}

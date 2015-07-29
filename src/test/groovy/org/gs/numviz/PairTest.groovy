package org.gs.numviz

// see end-of-file for license information


class PairTest extends GroovyTestCase {

    public void testEqualPair() {
        def p1 = new Pair(3,1)
        def p2 = new Pair(3,1)

        assertEquals( "$p1 and $p2 should be equal", p1, p2)
    }


    public void testGetPairWithQuasiFluentApi() {
        assertEquals( Pi.getPair(0), new Pair( 3,1) )


    }
}

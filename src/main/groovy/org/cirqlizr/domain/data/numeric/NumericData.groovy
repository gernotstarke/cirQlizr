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

/**
 * This is an abstract class that cannot be instantiated directly.
 * Known subclasses are @see Pi
 */
abstract class NumericData {

    // that value needs to be set by subclasses' constructor
    protected ArrayList<Integer> elements

    // how many elements are available
    // this value needs to be set by subclass
    protected int MAX_AVAILABLE_ELEMENTS

    // how many elements (elements) are available
    // in this implementation / instance?
    protected int NUMBER_OF_ELEMENTS

    // name of this number
    // in case of greek names, use unicode (see class Pi for an example)
    protected String name


    // in case no precision is configured
    // (used by subclass constructor)
    final static int DEFAULT_PRECISION = 10

    /**
     * @param position, starting with zero
     * @return data element at position
     */
    public int getElementAtIndex( int position) {
        if (indexIsWithinBounds(position) ) {
            return elements.get(position) }
        else throw new IndexOutOfBoundsException( "Position ${position} out of bounds, should be within 0 and ${elements.size()}" )
    }



    /**
     * counts the occurences of @param element in all elements. Example: Number="3.141", result for element 1 would be 2
     * @param element
     * @return nr of occurences, 0 if element does not occur
     */
    public int countElement( int element ) {
        assert element >= 0
        assert element <= 9

        return elements.count( element )
    }

    /**
     * counts the occurences within the first 0..N pairs. Example: Number = "3.14", element=1, N=2, result = 2
     * as pairs are (3,1), (1,4)
     * @param element
     * @param pairNr
     * @return how often does element occur in the first N pairs (where pairs count from 0 onwards)
     */
    public int countOccurencesInPairs( int element, int pairNr) {
        assert pairNr < NUMBER_OF_ELEMENTS : "number has only $NUMBER_OF_ELEMENTS elements, cannot get pairNr $pairNr "

        int pairsItOccursIn = 0

        getAllPairsUpTo( pairNr ).each { pair ->
            if (pair.first == element) pairsItOccursIn++
            if (pair.second == element) pairsItOccursIn++
        }

        return pairsItOccursIn
    }



    private boolean indexIsWithinBounds( int index ) {
        return (index >= 0) && (index < elements.size())
    }


    /**
     * get the Pair-relation
     * @param index
     * @return the pair at the specified index.
     */
    public  Pair getPair( int index ) {
        assert index < NUMBER_OF_ELEMENTS : "pair($index) out of bounds, only $NUMBER_OF_ELEMENTS available!"

        // old version: pair always consists of (digit_i, digit_i+1)
        //return new Pair( getElementAtIndex( index ), getElementAtIndex( index+1))

        // new version: pair is determined by this element and getRelationForElementAtIndex( int index)
        return new Pair( getElementAtIndex( index ), getRelationForElementAtIndex( index ))
    }


    /**
     * get the related element for the element at index
     *
     */
    public int getRelationForElementAtIndex( int index) {
        return getElementAtIndex( index+1)
    }

    /**
     * collects all pairs up to the Nth
     * @param pairNr
     * @return
     */
    public ArrayList<Pair> getAllPairsUpTo(int pairNr) {
        assert pairNr >= 0 : "pairNr should be >= 0"
        assert pairNr < NUMBER_OF_ELEMENTS: "pairNr should be less than NUMBER_OF_ELEMENTS ($NUMBER_OF_ELEMENTS)"

        return (0..pairNr).collect { getPair( it ) }
    }


}

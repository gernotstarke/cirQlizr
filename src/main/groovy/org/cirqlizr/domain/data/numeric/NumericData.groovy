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
    protected ArrayList<Integer> digits

    // how many digits are available
    // this value needs to be set by subclass
    protected int MAX_AVAILABLE_DIGITS

    protected int NUMBER_OF_DIGITS

    // name of this number
    // in case of greek names, use unicode (see class Pi for an example)
    protected String name


    // in case no precision is configured
    // (used by subclass constructor)
    final static int DEFAULT_PRECISION = 10


    public int getDigit( int position) {
        if (indexIsWithinBounds(position) ) {
            return digits.get(position) }
        else throw new IndexOutOfBoundsException( "Position ${position} out of bounds, should be within 0 and ${digits.size()}" )
    }



    /**
     * counts the occurences of @param digit in all digits. Example: Number="3.141", result for digit 1 would be 2
     * @param digit
     * @return nr of occurences, 0 if digit does not occur
     */
    public int countDigit( int digit ) {
        assert digit >= 0
        assert digit <= 9

        return digits.count( digit )
    }

    /**
     * counts the occurences within the first 0..N pairs. Example: Number = "3.14", digit=1, N=2, result = 2
     * as pairs are (3,1), (1,4)
     * @param digit
     * @param pairNr
     * @return how often does digit occur in the first N pairs (where pairs count from 0 onwards)
     */
    public int countOccurencesInPairs( int digit, int pairNr) {
        assert pairNr < NUMBER_OF_DIGITS : "number has only $NUMBER_OF_DIGITS digits, cannot get pairNr $pairNr "

        int pairsItOccursIn = 0

        getAllPairsUpTo( pairNr ).each { pair ->
            if (pair.first == digit) pairsItOccursIn++
            if (pair.second == digit) pairsItOccursIn++
        }

        return pairsItOccursIn
    }



    private boolean indexIsWithinBounds( int index ) {
        return (index >= 0) && (index < digits.size())
    }


    /**
     *
     * @param index
     * @return the number pair at the specified index.
     */
    public  Pair getPair( int index ) {
        assert index < NUMBER_OF_DIGITS : "pair($index) out of bounds, only $NUMBER_OF_DIGITS available!"
        return new Pair( getDigit( index ), getDigit( index+1))
    }


    /**
     * collects all pairs up to the Nth
     * @param pairNr
     * @return
     */
    public ArrayList<Pair> getAllPairsUpTo(int pairNr) {
        assert pairNr >= 0 : "pairNr should be >= 0"
        assert pairNr < NUMBER_OF_DIGITS: "pairNr should be less than NUMBER_OF_DIGITS ($NUMBER_OF_DIGITS)"

        return (0..pairNr).collect { getPair( it ) }
    }


}

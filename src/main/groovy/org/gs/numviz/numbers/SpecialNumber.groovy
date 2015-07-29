package org.gs.numviz.numbers
// see end-of-file for license information

/**
 * This is an abstract class that cannot be instantiated directly.
 * Known subclasses are @see Pi
 */
abstract class SpecialNumber {

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
    final static int DEFAULT_PRECISION = 10


    public int getDigit( int position) {
        if (indexIsWithinBounds(position) ) {
            return digits.get(position) }
        else throw new IndexOutOfBoundsException( "Position ${position} out of bounds, should be within 0 and ${digits.size()}" )
    }



    /**
     * counts the occurences of @param digit in all digits.
     * @param digit
     * @return nr of occurences, 0 if digit does not occur
     */
    public int countDigit( int digit ) {
        assert digit >= 0
        assert digit <= 9

        return digits.count( digit )
    }


    private boolean indexIsWithinBounds( int index ) {
        return (index >= 0) && (index < digits.size())
    }

    public  Pair getPair( int index ) {
        return new Pair( getDigit( index ), getDigit( index+1))
    }


}

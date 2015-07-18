package org.gs.numviz

// see end-of-file for license information


class Pi  {

    private static ArrayList<Integer> digits = [3,1,4,1,5,9,2,6,5,3,5,8,9]

    public static int getDigit( int position) {
        if (indexIsWithinBounds(position) ) {
            return digits.get(position) }
        else throw new IndexOutOfBoundsException( "Position ${position} out of bounds, should be within 0 and ${digits.size()}" )
    }


    private static boolean indexIsWithinBounds( int index ) {
        return (index >= 0) && (index < digits.size())
    }

    public static Pair getPair( int index ) {
        return new Pair( getDigit( index ), getDigit( index+1))
    }


}

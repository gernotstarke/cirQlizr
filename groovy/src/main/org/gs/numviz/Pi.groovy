package org.gs.numviz

// see end-of-file for license information


class Pi extends SpecialNumber {

    private static ArrayList<Integer> piDigits = [3,1,4,1,5,9,2,6,5,3,5,8,9]

    public static int getDigit( int position) {
        if (indexIsWithinBounds(position) ) {
            return piDigits.get(position) }
        else throw new IndexOutOfBoundsException( "Position ${position} out of bounds, should be within 0 and ${piDigits.size()}" )
    }


    private static boolean indexIsWithinBounds( int index ) {
        return (index >= 0) && (index < piDigits.size())
    }
}

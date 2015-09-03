package org.cirqlizr.domain.data


interface DataProvider {

    /**
     * @return name of this data (e.g. "Pi" for the number pi)
     */
    public getName()


    public getRelationsForElementNr( int elementNr )



}
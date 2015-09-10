// tag::DataProviderInterface[]

package org.cirqlizr.domain.data

interface DataProvider {

    /**
     * @return name of this data (e.g. "Pi" for the number pi)
     */
    public String getName()

    /**
     * @return list of allowed values (e.g. 0..9 for numbers if all elements shall be visualized)
     */
    public List<DataElement> getValueSet()


    /**
     * @param index == position, starting with zero
     * @return the Element at index within the data
     */
    public getElementAtIndex( int index)


    /**
     * @param index == position, starting with zero
     * @return the Element(s) within the data *related* to the Element at index
     */
    public DataElement getRelationForElementAtIndex( int index)


    /**
     * counts how often the @param element occurs as source or target of relations
     * (aka connections)
     * @param element
     * @return how often does element occur in relations
     */
    public int getTotalNrOfRelationsForElement( DataElement element)


}

// end::DataProviderInterface[]
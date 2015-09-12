package org.cirqlizr.domain.data


class DataElement {

    final String  value

    public DataElement( int numericValue ) {
        this.value = numericValue.toString()
    }

    public DataElement( String stringValue) {
        this.value = stringValue
    }

    public DataElement( Integer intValue ) {
        this.value = intValue.toString()
    }


    @Override
    public boolean equals(Object o){
        if ((o == null) ||
           (!(o instanceof org.cirqlizr.domain.data.DataElement))) return false

        DataElement de = (DataElement) o
        return this.value == de.value
    }

    @Override
    public String toString() {
        return this.value
    }
}



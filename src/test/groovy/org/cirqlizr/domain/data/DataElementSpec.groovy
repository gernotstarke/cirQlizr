package org.cirqlizr.domain.data

import spock.lang.Specification

class DataElementSpec extends Specification {
    DataElement dataElement

    def "can instantiate DataElement with int"() {

        int dozen = 12
        when:
            dataElement = new DataElement( dozen )

        then:
            dataElement.getValue() == dozen.toString()
    }

    def "can instantiate DataElement with String"() {
        String the = "the"
        when:
            dataElement = new DataElement( the )
        then:
            dataElement.getValue() == the
    }

    def "can instantiate DataElement with Integer"() {
        Integer i = 12
        when: dataElement = new DataElement( i )
        then: dataElement.getValue() == i.toString()

    }

    def "Comparing for Equality works for DataElement"() {
        Integer i = 12
        String the = "the"



        when:
            def de1 = new DataElement(i)
            def de2 = new DataElement(the)

        then:
            de1 == new DataElement(i)
            de2 == new DataElement(the)
    }

}

/************************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *********************************************************************** */


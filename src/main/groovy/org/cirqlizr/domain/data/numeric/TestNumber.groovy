package org.cirqlizr.domain.data

class TestNumber extends NumericData {

    private final static ArrayList<Integer> ALL_KNOWN_DIGITS =
            //[1,2,1,3,1,4,1,5,1,6,1,7,1,8,1,9,1,0,1]
            [7,2,6,3,5,9,9,9,9,9,9,9,9,9,9,9]
             /**
     create instance with a predefined precision
     **/
    public TestNumber( int precision ) {
        super()

        name = "Z_\u03C4_N (Zet_tau_N, Zorg42's test number)"
        MAX_AVAILABLE_DIGITS = ALL_KNOWN_DIGITS.size()

        // avoid misconfiguration
        assert precision > 0 // zero elements make no sense at all
        assert precision <= MAX_AVAILABLE_DIGITS


        if (precision <= MAX_AVAILABLE_DIGITS) {
            this.NUMBER_OF_DIGITS = precision
            this.elements = ALL_KNOWN_DIGITS[0..precision-1]
        }
        // else throw new MisconfigurationException("illegal precision ${precision}")

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


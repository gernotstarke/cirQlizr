package org.circulizr.ui

import spock.lang.Specification

import java.awt.Color

class CirculizrColorSpec extends Specification {

    def "find contrasting color to DARK_GRAY"() {
        given:
        Color grayColor = Color.DARK_GRAY

        expect:
            64 == grayColor.red
            64 == grayColor.green
            64 == grayColor.blue

        when:
        Color contrastingColor = CirculizrColor.findContrastingColorFor( grayColor )

        then:
            191 == contrastingColor.red
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


package org.circulizr.configuration

import org.circulizr.domain.ConnectionStyle
import org.circulizr.domain.numbers.SpecialNumber
import spock.lang.Specification

import java.awt.Color

class ConfigurationSpec extends Specification {

    String configString = """
    runmode = "PRODUCTION"

    ui_toolkit = "JAVA2D"

    valueSet = ["0", "1", "2", "3"]

    number="org.circulizr.domain.numbers.Pi"
    precision=123

    nr_of_connections_to_show = 42


    colors {
        // use java.awt.Color names here,
        // alternatively use hex values...
        background = DARK_GRAY
    }

    segments {

        // padding angle == angle between segments in degrees
        padding_angle = 1.5d

        // if same_size == false, all segments may have different size
        same_size     = true

        // sum of entries in sizes-array should be 360 (degrees)!
        // if sum is smaller, padding_angle is increased
        // larger sum results in errors
        extend = 30d

        //extends = [30, 50, 70, 120, 120]

        connection_distance = 0

        // distance between Bezier control point (BCP) and point-of-origin (POO)
         // default value is zero -> BCP for all segments == POO
        bcp_poo_distance = 0
    }

    connections {
        style = "BEZIER" // "STRAIGHT"

        stroke_width = 0.75d
    }


    legend {
        show = true
        statistics = true

    }


    resolution {
       output = 700
       internal = 1000
    }



    layout {
        // the margin between outer edge of drawing area and canvas/frame edge
        // given in physical coordinates
        margin = 20
    }



"""

    // specifiy a more-or-less complete configuration
    def "can configure completely"() {
        given:
        def config = new ConfigSlurper().parse( configString )
        Configuration configuration = new Configuration( config )

        expect:
        RunMode.PRODUCTION == configuration.RUNMODE

        ["0", "1", "2", "3"] == configuration.VALUESET

        // can configure number
        configuration.NUMBER instanceof SpecialNumber
        123 == configuration.PRECISION

        42 == configuration.NR_OF_CONNECTIONS_TO_SHOW

        Color.DARK_GRAY == configuration.BACKGROUND_COLOR

        0.75d == configuration.STROKE_WIDTH

        ConnectionStyle.BEZIER == configuration.CONNECTION_STYLE

        1000 == configuration.INTERNAL_RESOLUTION
         700 == configuration.OUTPUT_RESOLUTION

          20 == configuration.MARGIN


    }


    def "can convert configuration string to SpecialNumber instance"() {
        expect:
        Configuration.convertStringToSpecialNumberInstance( "org.circulizr.domain.numbers.Pi") instanceof SpecialNumber
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


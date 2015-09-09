/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Dr. Gernot Starke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cirqlizr.configuration

import org.cirqlizr.AssertHelper
import org.cirqlizr.domain.data.Pi
import org.cirqlizr.domain.data.NumericData
import spock.lang.Specification

class ConfigurationSlurperSpec extends Specification {


    def "can read string configuration"() {
        given:
        def stringConfig = """title="arc42" """
        def config = new ConfigSlurper().parse(stringConfig)

        expect:
        config.title == "arc42"

    }


    def "can read integer configuration"() {
        given:
        def numberConfig = """x_resolution = 700 """
        def config = new ConfigSlurper().parse(numberConfig)

        expect:
        config.x_resolution == 700

    }


    def "can read double configuration"() {
        given:
        def numberConfig = """angle = 3.141592653589793d """
        def config = new ConfigSlurper().parse(numberConfig)

        expect:
        AssertHelper.assertCloseTo(config.angle, Math.PI, 0.00000001)
    }


    def "can read runmode PRODUCTION configuration"() {
        given:
        def runmodeConfig = """runmode = "PRODUCTION" """
        def config = new ConfigSlurper().parse(runmodeConfig)

        expect:
        config.runmode == "PRODUCTION"
        RunMode.PRODUCTION == (config.runmode as RunMode)
    }

    def "can read runmode DEBUG configuration"() {
        given:
        def runmodeConfig = """runmode = "DEBUG" """
        def config = new ConfigSlurper().parse(runmodeConfig)

        expect:
        config.runmode == "DEBUG"
        RunMode.DEBUG == (config.runmode as RunMode)
    }

    def "can read numerical set configuration"() {
        given:
        def setConfig = """valueSet = [0,1,2]"""
        def config = new ConfigSlurper().parse(setConfig)

        expect:
        config.valueSet == [0, 1, 2]
    }


    def "can read string set configuration"() {
        given:
        def setConfig = """valueSet = ["a", "b", "c" ]"""
        def config = new ConfigSlurper().parse(setConfig)

        expect:
        config.valueSet == ["a", "b", "c"]


    }

    def "can convert configuration string to class instance by no-arc constructor"() {
        given:
        def specialNumberConfig = """ number="org.cirqlizr.domain.data.Pi"  """
        def config = new ConfigSlurper().parse(specialNumberConfig)

        when:
        // newInstance() invokes no-arg constructor
        NumericData number = Class.forName(config.number).newInstance()

        then:
        // found the right configuration string
        config.number == "org.cirqlizr.domain.data.Pi"

        // we created an instance of the correct class
        [Pi.class, NumericData.class].each {
            number.class in it
        }

        // we can access number ...
        number.getElementAtIndex(0) == 3
        number.getElementAtIndex(1) == 1

    }

    def "can convert configuration string to class instance by specific constructor"() {
        given:
        def specialNumberConfig = """ number="org.cirqlizr.domain.data.Pi"  """
        def config = new ConfigSlurper().parse(specialNumberConfig)
        def precision = 7

        when:
        // create Pi instance with defined precision
        NumericData number = Class.forName(config.number).newInstance(precision)

        then:
        number.NUMBER_OF_DIGITS == precision
    }


}

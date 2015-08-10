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
package org.circulizr

import org.circulizr.numbers.Pi
import org.circulizr.numbers.SpecialNumber
import spock.lang.Specification

import java.lang.reflect.Constructor



class ConfigurationSpec extends Specification {


    def "can read string configuration"() {
        given:
        def stringConfig = """runmode="PRODUCTION" """
        def config = new ConfigSlurper().parse(stringConfig)

        expect:
        config.runmode == "PRODUCTION"

    }


    def "can convert configuration string to class instance by no-arc constructor"() {
        given:
        def specialNumberConfig = """ number="org.circulizr.numbers.Pi"  """
        def config = new ConfigSlurper().parse(specialNumberConfig)
        def precision = 10

        when:
        // newInstance() invokes no-arg constructor
        SpecialNumber number = Class.forName(config.number).newInstance()

        then:
        // found the right configuration string
        config.number == "org.circulizr.numbers.Pi"

        // we created an instance of the correct class
        [Pi.class, SpecialNumber.class].each {
            number.class in it
        }

        // we can access number ...
        number.getDigit(0) == 3
        number.getDigit(1) == 1

    }

    def "can convert configuration string to class instance by specific constructor"() {
        given:
        def specialNumberConfig = """ number="org.circulizr.numbers.Pi"  """
        def config = new ConfigSlurper().parse(specialNumberConfig)
        def precision = 7

        when:
        // create Pi instance with defined precision
        SpecialNumber number = Class.forName(config.number).newInstance(precision)

        then:
        number.NUMBER_OF_DIGITS == precision
    }


}

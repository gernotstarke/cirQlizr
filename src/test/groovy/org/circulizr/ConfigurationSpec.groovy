package org.circulizr

import org.circulizr.numbers.Pi
import org.circulizr.numbers.SpecialNumber
import spock.lang.Specification

import java.lang.reflect.Constructor

// see end-of-file for license information


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

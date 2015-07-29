package org.gs.numviz.numbers

import org.gs.numviz.numbers.Pi
import org.gs.numviz.numbers.SpecialNumber
import spock.lang.Specification

// see end-of-file for license information


class SpecialNumberSpec extends Specification {

    SpecialNumber number

    def "pi is a nice and special number"() {
        when:
            number = new Pi( 5 ) // 3.1415

        then:
            number.getDigit( 0 ) == 3
            number.getDigit( 1 ) == 1

            number.countDigit( 3 ) == 1 // occurs once
            number.countDigit( 1 ) == 2 // occurs twice
            number.countDigit( 2 ) == 0 // does not occur
    }


    //@Unroll
    def "count digits works for different precisions of PI"(int precision, int digit, int count) {
        when:
            number = new Pi( precision )

        then:
            number.countDigit(digit) == count

        where:
        precision | digit | count
        // Pi(1) == 3
        1         |   3   | 1
        1         |   1   | 0
        1         |   2   | 0
        1         |   4   | 0
        1         |   5   | 0
        1         |   6   | 0
        1         |   7   | 0
        1         |   8   | 0
        1         |   9   | 0
        1         |   0   | 0

        // Pi(5) == 3.1415
        5         |   1   | 2
        5         |   2   | 0
        5         |   3   | 1
        5         |   4   | 1
        5         |   5   | 1
        5         |   6   | 0
        5         |   7   | 0
        5         |   8   | 0
        5         |   9   | 0
        5         |   0   | 0

        // Pi(10) == 3.141592653
        10        |   1   | 2
        10        |   2   | 1
        10        |   3   | 2
        10        |   4   | 1
        10        |   5   | 2
        10        |   6   | 2
        10        |   7   | 0
        10        |   8   | 0
        10        |   9   | 1
        10        |   0   | 0


    }
}

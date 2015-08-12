package org.circulizr.configuration

/**
 * To make the configuration foolproof :-)
 *
 * converts plain text and number config items to stronger typed attributes or constants.
 *
 * Used in combination with ConfigSlurper.
 */

class Configuration {


    public final RunMode RUNMODE



    public Configuration( config ) {
        this.RUNMODE = config.runmode as RunMode
    }
}



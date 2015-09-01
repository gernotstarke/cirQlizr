package org.cirqlizr.configuration

import org.cirqlizr.domain.ConnectionStyle
import org.cirqlizr.domain.data.SpecialNumber

import java.awt.Color

/**
 * To make the configuration foolproof :-)
 *
 * converts plain text and number config items to stronger typed attributes or constants.
 *
 * Used in combination with ConfigSlurper.
 */

class Configuration {

    // runmode must not be modified at runtime
    final RunMode RUNMODE

    // default: valueSet consists of all digits to visualize numeric
    // needs to be List (not Set), as we need indexed access due to SEGMENT_EXTEND_ANGLES
    // needs to have size() > 1
    List<String> VALUESET // ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]

    // TODO: rename number to pairProvider
    SpecialNumber NUMBER
    Integer       PRECISION

    // how many connections (==lines) to show?
    Integer NR_OF_CONNECTIONS_TO_SHOW

    // INFO_LINE is currently not configurable
    final String INFO_LINE = "Circular Visualizer, https://github.com/gernotstarke/cirQlizr  "

    // window title
    String TITLE_TEXT = "cirQlizr"


    // background color of canvas/drawing-area
    Color BACKGROUND_COLOR

    // what is the default angle (distance) between segments?
    Double SEGMENT_PADDING_ANGLE_DEG
    Double SEGMENT_PADDING_ANGLE_RAD

    // are all segments of the same size?
    // if true -> SEGMENT_SIZE = valueSet.size()
    Boolean SEGMENT_SAME_SIZE

    // allow segments to have different sizes
    List<Double> SEGMENT_EXTEND_ANGLE

    // distance between segments and connectionNodes, >= 0
    Double SEGMENT_CONNECTION_DISTANCE

    // distance between Bezier control point (BCP) and point-of-origin (POO)
    // default value is zero -> BCP for all segments == POO
    // unit: logical coordindates
    Double SEGMENT_BCP_POO_DISTANCE

    // Connections are usually drawn as (quadratic) Bezier curves,
    // but may also be straight lines
    ConnectionStyle CONNECTION_STYLE

    // thickness of connection
    Double STROKE_WIDTH

    // show bezier-control-points as white dot
    Boolean SHOW_BCP


    // if SHOW_LEGEND == true, distinct legend will be drawn
    // else segment-names will be drawn close to segments
    Boolean SHOW_LEGEND

    // numeric statistics at legend: show number of occurences in legend
    // (applies only if SHOW_LEGEND == true)
    Boolean SHOW_LEGEND_STATISTICS

    // window/canvas size, will create rectangle of this size for drawing the circle
    Integer OUTPUT_RESOLUTION

    // what size does the internal coordinate system have? This will be mapped
    // onto the OUTPUT_RESOLUTION by the UI classes
    Integer INTERNAL_RESOLUTION

    // the margin between outer edge of drawing area and canvas/frame edge
    Integer MARGIN

    /**
     * construct a valid and typed Configuration instance from a config-map (read by ConfigSlurper)
     * @param config
     */
    public Configuration(config) {
        this.RUNMODE = config.runmode as RunMode

        this.VALUESET = (List<String>) validListOfStrings(config.valueSet)

        this.PRECISION = (Integer) validNumber( config.precision )

        this.NUMBER = convertStringToSpecialNumberInstance(config.number, config.precision)

        this.NR_OF_CONNECTIONS_TO_SHOW = (Integer) validNumber(config.nr_of_connections_to_show)


        configureSegments(config)

        configureConnections( config )

        configureResolution( config )

        configureLayout( config )


        this.BACKGROUND_COLOR = Color.DARK_GRAY

        this.TITLE_TEXT = "CIRCULIZR - ${this.NR_OF_CONNECTIONS_TO_SHOW} digits of ${this.NUMBER.name}"
    }

    /**
     * With filename given, read configuration via ConfigSlurper
     * @param filename
     */
    public Configuration( String filename) {
        this( new ConfigSlurper().parse(new File('cirQlizr.config').toURL()))
    }



    private void configureLayout( config ) {
        this.MARGIN = (Integer) validNumber( config.layout.margin)
    }


    private void configureResolution( config ) {
        this.OUTPUT_RESOLUTION = (Integer) validNumber( config.resolution.output )
        this.INTERNAL_RESOLUTION = (Integer) validNumber( config.resolution.internal )
    }


    private void configureConnections( config ) {
        this.STROKE_WIDTH = (Double) validNumber( config.connections.stroke_width)

        this.CONNECTION_STYLE = config.connections.style as ConnectionStyle
    }



    private void configureSegments(config) {
        // do all segments shall have equal size (== extend)?
        this.SEGMENT_SAME_SIZE = (Boolean) validBoolean(config.segments.same_size)

        // if segments have identical size, we need the one segment_extend_angle
        if (this.SEGMENT_SAME_SIZE) {
            fillSegmentExtendAnglesWithIdenticalValue(config.segments.extend)
        }
        // when segments differ in size, we need a list of segment-extend-angles
        else {
            this.SEGMENT_EXTEND_ANGLE = (List<Double>) validListOfDoubles(config.segments.extends)
        }

        this.SEGMENT_PADDING_ANGLE_DEG = (Double) validNumber(config.segments.padding_angle)

        this.SEGMENT_CONNECTION_DISTANCE = (Double) validNumber(config.segments.connection_distance )

        this.SEGMENT_BCP_POO_DISTANCE = (Double) validNumber( config.segments.bcp_poo_distance)

    }


    private void fillSegmentExtendAnglesWithIdenticalValue( extend ) {
        double theExtendAngle = (Double) validNumber( extend )
        SEGMENT_EXTEND_ANGLE = new ArrayList<Double>()
        this.VALUESET.each {
            SEGMENT_EXTEND_ANGLE.add(theExtendAngle)
        }
    }


    public static List<String> validListOfStrings(potentialStringList) {
        assert potentialStringList instanceof java.util.List: "$potentialStringList must be of type 'java.util.List', but isn't. Aborted."
        assert potentialStringList.size() > 1: "$potentialStringList needs more than one element, but hasn't. Aborted."
        return potentialStringList
    }


    public static List<Double> validListOfDoubles( potentialDoublesList) {
        assert potentialDoublesList instanceof java.util.List: "$potentialDoublesList must be of type 'java.util.List', but isn't."
        return (ArrayList<Double>) potentialDoublesList
    }

    /**
     * If @param is number, return it. Abort otherwise.
     */
    public static Number validNumber(def potentialNumber) {
        assert potentialNumber instanceof java.lang.Number: "$potentialNumber must be numeric, but isn't! Aborted."
        return potentialNumber
    }

    /**
     * If @param is boolean, return it. Abort otherwise.
     */
    public static Boolean validBoolean(def potentialBoolean) {
        assert potentialBoolean instanceof java.lang.Boolean: "$potentialBoolean must be boolean, but isn't. Aborted."
        return potentialBoolean
    }

    /**
     * convert String to SpecialNumber instance
     * @param configuredSpecialNumber
     * @return
     */
    public static SpecialNumber convertStringToSpecialNumberInstance(String configuredSpecialNumber, Integer precision) {
        assert precision > 0 : "precision 0 makes no sense - aborted!"
        //this.PRECISION = precision

        return Class.forName(configuredSpecialNumber).newInstance( precision )
    }


    private boolean isValidSpecialNumber(potentialValue) {
        return ((potentialValue instanceof SpecialNumber))
    }

}



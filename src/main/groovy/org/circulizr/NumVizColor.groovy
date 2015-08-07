package org.circulizr

import java.awt.Color

/**
 * 10-class spectral color scheme, as proposed by http://colorbrewer2.org:
 * http://colorbrewer2.org/?type=diverging&scheme=Spectral&n=10
 */

class NumVizColor {

    // private static final List<Integer> colNumbers =
    //         Arrays.asList(0x67001F,0xb2182B,0xb2182B,0xb2182B, 0xf4a582, 0xfddbc7, 0xd1e5f0 )

    // scheme for diverging data
    public final static List<Color> divergingColor = Arrays.asList(
            new Color(158, 1, 66),
            new Color(213, 62, 79),
            new Color(244, 109, 67),
            new Color(253, 174, 97),
            new Color(254, 224, 139),
            new Color(230, 245, 152),
            new Color(171, 221, 164),
            new Color(102, 194, 165),
            new Color(50, 136, 189),
            new Color(94, 79, 162)
    )

    // scheme for qualitative data, more colors
    // see: http://colorbrewer2.org/?type=qualitative&scheme=Paired&n=10
    public final static List<Color> qualitativeColor = Arrays.asList(
            new Color(166, 206, 227),
            new Color(31, 120, 180),
            new Color(178, 223, 138),
            new Color(51, 160, 44),
            new Color(251, 154, 153),
            new Color(227, 26, 28),
            new Color(253, 191, 111),
            new Color(255, 127, 0),
            new Color(202, 178, 214),
            new Color(106, 61, 154))


    public static List<Color> color = qualitativeColor
}

/*********************************************************************************
 The MIT License (MIT)

 Copyright (c) 2015 Dr. Gernot Starke

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 *********************************************************************************/

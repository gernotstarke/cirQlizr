Ich# num-viz
Number visualization - similar to http://mkweb.bcgsc.ca/pi/art/method.mhtml

A simple experiment with 2D graphics.

## Goal
The result shall at least look similar to the awesome [mother of circular pi visualisations](http://thecreatorsproject.vice.com/blog/visualising-the-infinite-data-of-pie)

There's even a nice [Numberphile video](https://www.youtube.com/watch?v=NPoj8lk9Fo4) on this kind of graphics.


## Principle

A coarse overview by the [original authors](http://mkweb.bcgsc.ca/pi/art/method.mhtml),
Martin Krzywinski and Cristian Illies Vasile.

1. divide circle into 10 segments, one for each digit
2. for each pair of digits within a number, draw a curve from first-to-second.
3. transition the color of this curve
4. the position of the curve on the segment is determined by the position of the digits


## Domain Terminology

![number visualization domain](numviz-domain.jpg)

* The number to be visualized consists of ordered Digits.
* Pair consists of left ("from") and right ("to") Digit,
* Line represents graphical connection of "from" (left) and "to" (right) Digit of a Pair.
* Segment:
  * visually represents all Lines for one specific Digit (either left or right in a Pair).
  * contains list of DigiNodes and determines their x/y positions
  on the drawing canvas.
  * has a position on drawing canvas
  * has a shape (in the image above its rectangular,
    in more appealing visuals it should be an arc)
* Line
  * corresponds to a Pair of Digits.
  * Start and end of the line are DigiNodes

#### Open Issues
* what is the radius of the curve drawn?
* Is it Bezier-style?
* What to do with lines from i to i?



## Color Selection
Selecting color schemes for visualizations should *not* only based
upon pure asthetic aspects, but on research...

[Cynthia Bewer](http://colorbrewer2.org/) gives serious advice on this topic.  


## Numbers
I used the following resources for getting the numbers:

* [pi: Angio.net](http://www.angio.net/pi/digits.html)

and helped myself with a small script to convert the plain format to
comma-separated digits useable as static ArrayList initializer:

    def numAsString =
    """3.1415926535 8979323846 2643383279 5028841971 6939937510
       5820974944 5923078164 0628620899 8628034825 3421170679
    """

    def numAsArrayListStr = "["

    def String processSingleChar( String singleC ) {
      if ( singleC.isInteger()) return singleC + ","
        else return ""
    }

    for(int i = 0; i < numAsString.length(); i++) {
        numAsArrayListStr +=
                processSingleChar( numAsString.charAt(i).toString())

        if ((i>0) && (i % 50) == 0) numAsArrayListStr += "\n "
    }

    println numAsArrayListStr


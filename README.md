# num-viz
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

#### Open Issues
* what is the radius of the curve drawn? 
* Is it Bezier-style?   
* What to do with lines from i to i? 



## Color Selection
Selecting color schemes for visualizations should *not* only based
upon pure asthetic aspects, but on research...

[Cynthia Bewer](http://colorbrewer2.org/) gives serious advice on this topic.  




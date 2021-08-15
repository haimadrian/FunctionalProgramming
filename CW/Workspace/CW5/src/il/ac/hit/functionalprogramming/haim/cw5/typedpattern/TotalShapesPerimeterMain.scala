package il.ac.hit.functionalprogramming.haim.cw5.typedpattern

import il.ac.hit.functionalprogramming.haim.cw5.typedpattern.shape.{Circle, CircleInCircle, Point, Rectangle, Shape}

/**
 * Develop a simple application that calculates the total of all shapes' perimeters.
 * The shapes should be represented with objects instantiated from Circle and Rectangle.
 * Both classes should extend Shape, an abstract class that includes the declaration for the perimeter abstract method.
 *
 * The shapes include the following:
 * Circle, radius=3
 * Circle, radius=2
 * Rectangle, width=5 height=4
 *
 * @author Haim Adrian
 * @since 01 Aug 2021
 */
object TotalShapesPerimeterMain {
  def main(args: Array[String]): Unit = {
    val point = new Point(0, 0)
    val shapes = List[Shape](new Circle(point, 3), new Circle(point, 2), new Rectangle(point, 5, 4), new CircleInCircle(point, 2, 5))

    // mkString works like Collectors.joining, as if we would like to join list variables to string, using specific separator
    println(s"Shapes:${System.lineSeparator()}${shapes.mkString(System.lineSeparator())}")

    println()
    println("Typed Pattern:")
    for (shape <- shapes) {
      shape match {
        case s: Circle => println(s"Circle: $s")
        case s: Shape => println(s"Shape: $s")
        case _ => println("Default")
      }
    }
  }
}

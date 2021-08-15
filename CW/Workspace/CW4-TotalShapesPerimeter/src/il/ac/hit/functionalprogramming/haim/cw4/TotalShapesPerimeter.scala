package il.ac.hit.functionalprogramming.haim.cw4

import il.ac.hit.functionalprogramming.haim.cw4.shape.{Circle, Point, Rectangle, Shape}

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
object TotalShapesPerimeter {
  def main(args: Array[String]): Unit = {
    val point = new Point(0, 0)
    val shapes = List[Shape](new Circle(point, 3), new Circle(point, 2), new Rectangle(point, 5, 4))

    // mkString works like Collectors.joining, as if we would like to join list variables to string, using specific separator
    println(s"Shapes:${System.lineSeparator()}${shapes.mkString(System.lineSeparator())}")

    // mapToDouble, to map all shapes to their perimeter, then sum up all values
    // The _.perimeter is like Shape::perimeter in Java.
    println(s"${System.lineSeparator()}Total perimeter: ${shapes.map(_.perimeter).sum}")
  }
}

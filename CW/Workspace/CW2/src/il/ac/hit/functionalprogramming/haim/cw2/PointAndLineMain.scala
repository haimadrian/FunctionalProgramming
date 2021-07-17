package il.ac.hit.functionalprogramming.haim.cw2

/**
 * You should define the class Point.
 * Each and every object instantiated from Point represents a point.
 * Each and every object instantiated from Point should have two variables: x and y.
 * You should define the class Line.
 * Each and every object instantiated from Line should include two variables: point1 and point2.
 * Both of them should be of the type Point.
 * You should define the LineUtils object. It should include a method for checking whether two lines are parallel or not.
 * It should be a method with two parameters of the type Line.
 *
 * @author Haim Adrian
 * @since 11-Jul-21
 */
object PointAndLineMain {
  def main(args: Array[String]): Unit = {
    val line1 = new Line(new Point(1, 1), new Point(3, 3))
    val line2 = new Line(new Point(2, 2), new Point(7, 7))
    println(s"Line1=$line1, Line2=$line2. Is Parallel? ${LineUtils.isParallel(line1, line2)}")

    val line3 = new Line(new Point(2, 2), new Point(2, 7))
    println(s"Line1=$line1, Line3=$line3. Is Parallel? ${LineUtils.isParallel(line1, line3)}")
  }
}

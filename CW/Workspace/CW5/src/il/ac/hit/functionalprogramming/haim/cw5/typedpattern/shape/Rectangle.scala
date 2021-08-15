package il.ac.hit.functionalprogramming.haim.cw5.typedpattern.shape

/**
 * @author Haim Adrian
 * @since 1 Aug 2021
 */
class Rectangle(point: Point, val width: Double, val height: Double) extends Shape(point) {
  override def calculateArea: Double = width * height

  override def calculatePerimeter: Double = 2 * (width + height)

  override def toString: String = s"Rectangle{width=$width, height=$height, ${super.toString}}"
}

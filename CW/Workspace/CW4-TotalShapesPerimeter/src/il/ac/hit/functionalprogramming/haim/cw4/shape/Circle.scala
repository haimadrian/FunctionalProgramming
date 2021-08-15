package il.ac.hit.functionalprogramming.haim.cw4.shape

/**
 * @author Haim Adrian
 * @since 1 Aug 2021
 */
class Circle(point: Point, val radius: Double) extends Shape(point) {
  override def calculateArea: Double = Math.PI * Math.pow(radius, 2)

  override def calculatePerimeter: Double = 2 * Math.PI * radius

  override def toString: String = s"Circle{radius=$radius, ${super.toString}}"
}

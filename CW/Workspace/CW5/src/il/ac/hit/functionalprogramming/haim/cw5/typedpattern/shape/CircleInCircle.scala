package il.ac.hit.functionalprogramming.haim.cw5.typedpattern.shape

/**
 * @author Haim Adrian
 * @since 1 Aug 2021
 */
class CircleInCircle(point: Point, radius: Double, val radius2: Double) extends Circle(point, radius) {
  override def calculateArea: Double = Math.PI * Math.pow(Math.max(radius, radius2), 2)

  override def calculatePerimeter: Double = 2 * Math.PI * Math.max(radius, radius2)

  override def toString: String = s"CircleInCircle{${super.toString}, radius2=$radius2}"
}

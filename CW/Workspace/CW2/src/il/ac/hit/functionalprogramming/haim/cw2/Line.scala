package il.ac.hit.functionalprogramming.haim.cw2

/**
 * Line consists of two points
 * @param point1
 * @param point2
 */
class Line(val point1: Point, val point2: Point) {
  //require (point1.x != point2.x)

  // Must multiply by 1.0 from left, as *,/ operators order is left to right, and we want the result to be
  // of type Double and not Int.
  def slope(): Double = if (point1.x == point2.x) Double.PositiveInfinity else 1.0 * (point2.y - point1.y) / (point2.x - point1.x)
  override def toString = s"{$point1, $point2}"
}
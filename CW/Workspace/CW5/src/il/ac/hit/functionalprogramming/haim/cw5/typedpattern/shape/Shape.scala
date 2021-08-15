package il.ac.hit.functionalprogramming.haim.cw5.typedpattern.shape

/**
 * @author Haim Adrian
 * @since 1 Aug 2021
 */
abstract class Shape(val point: Point) {
  /**
   * Area is calculated once, upon accessing this value.
   * There is no need to re-calculate area over and over as shape is Immutable.
   */
  private lazy val areaCached: Double = calculateArea

  /**
   * Perimeter is calculated once, upon accessing this value.
   * There is no need to re-calculate perimeter over and over as shape is Immutable.
   */
  private lazy val perimeterCached: Double = calculatePerimeter

  protected def calculateArea: Double
  protected def calculatePerimeter: Double

  final def area: Double = areaCached

  final def perimeter: Double = perimeterCached

  override def toString: String = s"point=$point, area=$area, perimeter=$perimeter"
}

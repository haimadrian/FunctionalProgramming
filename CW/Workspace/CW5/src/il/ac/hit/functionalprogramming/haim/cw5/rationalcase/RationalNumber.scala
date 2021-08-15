package il.ac.hit.functionalprogramming.haim.cw5.rationalcase

/**
 * @author Haim Adrian
 * @since 8 Aug 2021
 */
case class RationalNumber(numerator: Long, denominator: Long) {
  if (denominator == 0) throw new IllegalArgumentException("Cannot divide by zero!")
  def value: Double = numerator.toDouble / denominator.toDouble

  override def toString: String = {
    this match {
      case RationalNumber(Long.MaxValue, 1) => "+infinity"
      case RationalNumber(Long.MinValue, 1) => "-infinity"
      case RationalNumber(0, _) => "zero"
      case _ => s"$value"
    }
  }
}

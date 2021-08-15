package il.ac.hit.functionalprogramming.haim.cw5.rationalcase

/**
 * Define a class that describes a rational number. It should be defined as a case class. Each object of that class
 * should have two variables: numerator and denominator.
 *
 * Develop a small program that uses patterns matching for printing "zero" or "+-infinity" or a number with a decimal point.
 *
 * @author Haim Adrian
 * @since 8 Aug 21
 */
object RationalCaseMain {
  def main(args: Array[String]): Unit = {
    val numbers = List[RationalNumber](RationalNumber(25, 88),
      RationalNumber(25, 25),
      RationalNumber(0, 25),
      RationalNumber(Long.MaxValue, 1),
      RationalNumber(Long.MinValue, 1))

    // mkString works like Collectors.joining, as if we would like to join list variables to string, using specific separator
    println(s"Numbers:${System.lineSeparator()}${numbers.mkString(System.lineSeparator())}")
  }
}

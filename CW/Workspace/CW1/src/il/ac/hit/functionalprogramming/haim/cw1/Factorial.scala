package il.ac.hit.functionalprogramming.haim.cw1

/**
 * Define the factorial function. It should calculate the factorial result for the number it receives.
 * It should be a recursive function. Test your function by calculating the factorial of 4.
 *
 * @author Haim Adrian
 * @since 06-Jul-21
 */
object Factorial {
  def main(args: Array[String]): Unit = {
    for (i <- 0 to 4) {
      println(s"factorial($i)=${factorial(i)}")
    }
  }

  def factorial(num: Int): BigInt = {
    var result: BigInt = num

    if (num > 1) {
      result *= factorial(num - 1)
    }

    result
  }
}

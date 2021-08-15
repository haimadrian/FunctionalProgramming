package il.ac.hit.functionalprogramming.haim.cw5.factorial

/**
 * Define the factorial function. It should calculate the factorial result for the number it receives.
 * It should be a recursive function. Test your function by calculating the factorial of 4.
 *
 * @author Haim Adrian
 * @since 8 Aug 21
 */
object FactorialMain {
  def main(args: Array[String]): Unit = {
    val num = 6
    println(s"factorial($num)=${factorial(num)}")
  }

  def factorial(num: Int): BigInt = {
    num match {
      case a if (a <= 1) => num
      case _ => num * factorial(num - 1)
    }
  }
}

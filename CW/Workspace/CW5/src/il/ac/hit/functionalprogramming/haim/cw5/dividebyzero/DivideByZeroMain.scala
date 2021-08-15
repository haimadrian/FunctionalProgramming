package il.ac.hit.functionalprogramming.haim.cw5.dividebyzero

/**
 * You should develop an application that includes the definition for divide, a function that receives two Int values
 * and returns the division of the first by the second. If the second number is 0 that function should throw an
 * exception of the IllegalArgumentException type.
 * You should call that function from within a try block capable of catching the thrown exception printing out its details.
 *
 * @author Haim Adrian
 * @since 8 Aug 2021
 */
object DivideByZeroMain {
  def main(args: Array[String]): Unit = {
    try {
      println(s"Divide(4, 2): ${divide(4, 2)}")
      println(s"Divide(4, 0): ${divide(4, 0)}")
    } catch {
      case e: IllegalArgumentException => println(s"Error: ${e.getMessage}")
    }
  }

  def divide(num1: Double, num2: Double): Double = {
    num2 match {
      case 0 => throw new IllegalArgumentException(s"Cannot divide by zero! num1=$num1, num2=$num2")
      case _ => num1 / num2
    }
  }
}

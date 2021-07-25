package il.ac.hit.functionalprogramming.haim.cw3.scala

/**
 * Develop a simple application that creates a list with the following numbers: 3,5,2,32,43,30 and 321.
 * Your application should call the filter method on the list object passing over the required anonymous
 * function in order to get a new sub list composed of all numbers that divide by 3 without any residual.
 * Your application should print out the new list to the screen.
 *
 * @author Haim Adrian
 * @since 25-Jul-21
 */
object FilterListUsingAnonymousLambda {
  def main(args: Array[String]): Unit = {
    val lst = List(3, 5, 2, 32, 43, 30, 321)
    println(s"List: $lst")
    println(s"Numbers divided by 3: ${lst.filter(_ % 3 == 0)}")
  }
}

package il.ac.hit.functionalprogramming.haim.cw1

/**
 * You should develop a simple application that creates a list that includes the following numbers: 34,73,21 and 9.
 * Your application should calculate the sum of these numbers and print it out to the screen.
 *
 * @author Haim Adrian
 * @since 06-Jul-21
 */
object SimpleList {
  def main(args: Array[String]): Unit = {
    val list = List(34, 73, 21, 9)
    println(list.sum)
  }
}

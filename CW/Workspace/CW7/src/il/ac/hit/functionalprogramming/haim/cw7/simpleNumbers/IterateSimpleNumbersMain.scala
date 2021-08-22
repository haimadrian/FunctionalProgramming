package il.ac.hit.functionalprogramming.haim.cw7.simpleNumbers

/**
 * Develop a simple range that includes all numbers in between 1 and 10 (included). Write the required code for
 * iterating that range and printing out to the screen all numbers in between 1 and 10 (included).
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
object IterateSimpleNumbersMain {
  def main(args: Array[String]): Unit = {
    val range = 1 to 10
    range.foreach(println)
  }
}

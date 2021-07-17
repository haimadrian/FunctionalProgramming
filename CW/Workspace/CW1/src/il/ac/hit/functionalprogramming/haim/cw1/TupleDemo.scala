package il.ac.hit.functionalprogramming.haim.cw1

/**
 * Develop a small application that creates a tuple with the following values: 10, "canada" and 4.5.
 * Your application should access each one of the new created tuple elements and print it out to the screen.
 *
 * @author Haim Adrian
 * @since 06-Jul-21
 */
object TupleDemo {
  def main(args: Array[String]): Unit = {
    val tuple = (10, "canada", 4.5)

    println(s"toString: $tuple")

    println(s"${System.lineSeparator()}foreach:")
    tuple.productIterator.foreach(println)

    println(s"${System.lineSeparator()}Manual:")
    println(tuple._1)
    println(tuple._2)
    println(tuple._3)
  }
}

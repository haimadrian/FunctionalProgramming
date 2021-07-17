package il.ac.hit.functionalprogramming.haim.cw1

/**
 *
 * @author Haim Adrian
 * @since 06-Jul-21
 */
object LazyDemo {
  def main(args: Array[String]): Unit = {
    var a = 3
    val b = 4
    lazy val c = a + b
    a = 4
    println(c)
    a = 3
    println(c)
  }
}

package il.ac.hit.functionalprogramming.haim.cw7.recwithprops

/**
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
object RectangleMain {
  def main(args: Array[String]): Unit = {
    val rectangle = new Rectangle(0, 0)

    rectangle.width = 20
    rectangle.height = 20

    try {
      println("Setting negative width to catch exception")
      rectangle.width = -1
    } catch {
      case e: IllegalArgumentException => println(e)
      case e: Throwable => println(s"Unexpected error: $e")
    }

    println(rectangle)
  }
}

package il.ac.hit.functionalprogramming.haim.cw6.mapdemo

/**
 * @author Haim Adrian
 * @since 15 Aug 2021
 */
object MapDemoMain {
  def main(args: Array[String]): Unit = {
    val map = Map("Israel" -> "Jerusalem", "England" -> "London")
    val some: Option[String] = map.get("Israel")
    val some2: Option[String] = map.get("Italy")

    println(s"map.get(\"Israel\"): $some")
    println(s"map.get(\"Italy\"): $some2")
    println(s"some.get: ${some.get}")

    // Make sure it is not None, otherwise we fail with "NoSuchElementException"
    if (some2.isDefined) {
      println(some2.get)
    }

    val lst = List(1, 4, 2, 6, 3)
    println(lst.reduceLeft(Math.max))
  }
}

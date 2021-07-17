package il.ac.hit.functionalprogramming.haim.cw1

/**
 *
 * @author Haim Adrian
 * @since 05-Jul-21
 */
object Hello {
  println("Hello World!")

  def main(args: Array[String]): Unit = {
    println("Hello Main")
    println("sum(1, 4)=" + sum(1, 4))
    println("sum(1, 4, 5)=" + sum(1, 4, 5))
  }

  override def toString: String = super.toString

  def doSomething(): Unit = {
    println("Do something")
  }

  def sum(nums: Int*): Int = nums.sum
}

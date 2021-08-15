package il.ac.hit.functionalprogramming.haim.cw5.operatoroverloading

/**
 * Develop the Account class.
 * It should include the balance attribute. This is the only attribute it should include.
 * The Account class should overload the + operator.
 *
 * @author Haim Adrian
 * @since 8 Aug 21
 */
object OperatorOverloadingMain {
  def main(args: Array[String]): Unit = {
    val acc1 = Account(5)
    val acc2 = Account(7)

    println(s"Account1: $acc1")
    println(s"Account2: $acc2")
    println(s"Account1 + Account2: ${acc1 + acc2}")
  }
}

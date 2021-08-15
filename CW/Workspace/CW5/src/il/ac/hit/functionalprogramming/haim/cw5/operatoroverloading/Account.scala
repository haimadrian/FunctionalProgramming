package il.ac.hit.functionalprogramming.haim.cw5.operatoroverloading

/**
 * @author Haim Adrian
 * @since 8 Aug 2021
 */
case class Account(balance: Double) {
  def +(another: Account): Account = Account(balance + another.balance)
  def -(another: Account): Account = Account(balance - another.balance)
  def *(another: Account): Account = this * another.balance
  def *(another: Double): Account = Account(balance * another)
  def /(another: Account): Account = this / another.balance
  def /(another: Double): Account = if (another == 0) throw new IllegalArgumentException("Cannot divide by zero!") else Account(balance / another)

  override def toString: String = s"balance=$balance"
}

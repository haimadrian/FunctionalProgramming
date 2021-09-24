package il.ac.hit.functionalprogramming.finalproj.expenses.services

import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo

import java.util.Date

/**
 * A trait to expose CRUD functionality for expenses table.<br/>
 * We inject a mongo implementation of this trait.
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
trait ExpenseService {
  /**
   * Inserts a new expense document to mongo DB expenses collection
   *
   * @param userId    The userId (firebase) to use, received from client
   * @param userEmail The email of the registered user
   * @return Optional user info. (UserInfo upon success, and None upon failure)
   */
  def insertExpense(userId: String,
                    sum: Int,
                    currency: String,
                    category: String,
                    description: String,
                    date: Date): Option[ExpenseInfo]

  /**
   * Read a user document from mongo DB users collection
   *
   * @param userId The userId (firebase) to find, received from client
   * @return Optional user info. (UserInfo upon success, and None upon failure or not exists)
   */
  def findExpenses(userId: String, page: Int, limit: Int): Option[Seq[ExpenseInfo]]

  /**
   * Update a user document in mongo DB users collection
   *
   * @param userId   The userId (firebase) to update, received from client
   * @param userInfo User info to update. (Email is ignored)
   * @return Optional user info. (UserInfo upon success, and None upon failure)
   */
  def deleteExpense(userId: String, expenseId: String): Option[ExpenseInfo]

  def countExpenses(userId: String): Long
}

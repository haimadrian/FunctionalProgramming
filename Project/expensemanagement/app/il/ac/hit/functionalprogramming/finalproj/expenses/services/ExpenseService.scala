package il.ac.hit.functionalprogramming.finalproj.expenses.services

import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo

import java.util.Date

/**
 * A trait to expose CRUD functionality for expenses collection.<br/>
 * We inject a mongo implementation of this trait.
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
trait ExpenseService {
  /**
   * Inserts a new expense document to mongo DB expenses collection
   *
   * @param userId      The userId (firebase) to use, received from client's JWT
   * @param sum         Amount of expense
   * @param currency    What currency to use (ILS, USD, GBP, etc.)
   * @param category    Category of the expense, so we'll use for statistics. (e.g. HEALTH)
   * @param description A description for the expense
   * @param date        Date of the expense
   * @return Optional expense info. ([[ExpenseInfo]] upon success, and [[None]] upon failure)
   */
  def insertExpense(userId: String,
                    sum: Double,
                    currency: String,
                    category: String,
                    description: String,
                    date: Date = new Date(System.currentTimeMillis())): Option[ExpenseInfo]

  /**
   * Read an expense document from mongo DB expenses collection
   *
   * @param userId The userId (firebase) to find, received from client's JWT
   * @param page   What page to read (for Pagination)
   * @param limit  How much rows to select (also, for Pagination)
   * @return Optional expense info. ([[ExpenseInfo]] upon success, and [[None]] upon failure or not exists)
   */
  def findExpenses(userId: String, page: Int = 0, limit: Int = Int.MaxValue): Option[Seq[ExpenseInfo]]

  /**
   * Read all expense documents from mongo DB expenses collection
   *
   * @param startTimeMillis Start date to read expenses since
   * @param endTimeMillis   End date to read expenses until
   * @return Optional expense info. ([[ExpenseInfo]] upon success, and [[None]] upon failure or not exists)
   */
  def findAllExpenses(startTimeMillis: Long = 0,
                      endTimeMillis: Long = System.currentTimeMillis()): Option[Seq[ExpenseInfo]]

  /**
   * Delete an expense document from mongo DB expenses collection
   *
   * @param userId    The userId (firebase) to verify this expense belongs to that user
   * @param expenseId The Mongo ObjectId of the expense to delete
   * @return Amount of deleted documents (1 for success, 0 means not found)
   */
  def deleteExpense(userId: String, expenseId: String): Long

  /**
   * Count how many expenses there ar for a specified user. This is used by React for
   * implementing Pagination.
   *
   * @param userId The userId (firebase) to find, received from client's JWT
   * @return Amount of expenses a specified user got.
   */
  def countExpenses(userId: String): Long
}

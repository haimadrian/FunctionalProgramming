package il.ac.hit.functionalprogramming.finalproj.expenses.stats.services

import il.ac.hit.functionalprogramming.finalproj.expenses.stats.models.StatisticsInfo

/**
 * A trait to expose CRUD functionality for expenses collection.<br/>
 * We inject a mongo implementation of this trait.
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
trait StatisticsService {
  /**
   * Inserts a new statistics calculation to the mongo DB collection
   *
   * @param statistics Statistics to insert to MongoDB
   * @return Optional expense info. ([[StatisticsInfo]] upon success, and [[None]] upon failure)
   */
  def insertDailyStatistics(statistics: Seq[StatisticsInfo]): Boolean

  /**
   * Inserts a new statistics calculation to the mongo DB collection
   *
   * @param statistics Statistics to insert to MongoDB
   * @return Optional expense info. ([[StatisticsInfo]] upon success, and [[None]] upon failure)
   */
  def insertMonthlyStatistics(statistics: Seq[StatisticsInfo]): Boolean

  /**
   * Read statistics from monthly / daily collections, depends on the month parameter.<br/>
   * When there is a month specified, it means we should pull daily statistics. Otherwise,
   * we should pull monthly statistics for the specified year.
   *
   * @param userId The userId (firebase) to find, received from client's JWT
   * @param year   What year to get data for
   * @param month  What month to get data for
   * @return Optional statistics info. ([[StatisticsInfo]] upon success, and [[None]] upon failure or not
   *         exists)
   */
  def findStatistics(userId: String, year: Int = 2021, month: Option[Int] = None): Option[Seq[StatisticsInfo]]
}

package il.ac.hit.functionalprogramming.finalproj.expenses.stats.services

import com.mongodb.client.model.Filters
import il.ac.hit.functionalprogramming.finalproj.common.services.MongoService
import il.ac.hit.functionalprogramming.finalproj.common.utils.MongoUtils._
import il.ac.hit.functionalprogramming.finalproj.expenses.stats.models.StatisticsInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.stats.models.StatisticsInfo
.{CATEGORY_TO_EXPENSES, DATE, USER_ID}
import org.bson.conversions.Bson
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts
import org.mongodb.scala.{Document, MongoCollection}
import play.api.libs.json.Json
import play.api.{Configuration, Logger}

import java.time.{LocalDateTime, ZoneOffset}
import java.util.Date
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.FiniteDuration
import scala.language.postfixOps

/**
 * This class is a service of the data layer which exposes functionality to perform CRUD
 * operations on expenses collection.<br/>
 * It encapsulates the code relevant to MongoDB, such that it is easier to replace it
 * with another injection of [[StatisticsService]].
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
@Singleton
class StatisticsMongoDBService @Inject()(config: Configuration,
                                         mongo: MongoService) extends StatisticsService {
  /**
   * Use default time-out of 5 seconds for mongo tasks.
   */
  private val MONGO_TIMEOUT = config.get[FiniteDuration]("mongodb.timeout")

  /**
   * dailyexpenses collection name
   */
  private val MONGO_DAILY_COLLECTION = "dailyexpenses"

  /**
   * monthlyexpenses collection name
   */
  private val MONGO_MONTHLY_COLLECTION = "monthlyexpenses"

  private val logger: Logger = Logger(this.getClass)

  /**
   * The dailyexpenses Mongo collection. Don't use a val as Play Framework messes up with it during refresh
   *
   * @return dailyexpenses collection
   */
  private def dailyExpenses: MongoCollection[Document] = {
    mongo.db.getCollection(MONGO_DAILY_COLLECTION)
  }

  /**
   * The monthlyexpenses Mongo collection. Don't use a val as Play Framework messes up with it during refresh
   *
   * @return monthlyexpenses collection
   */
  private def monthlyExpenses: MongoCollection[Document] = {
    mongo.db.getCollection(MONGO_MONTHLY_COLLECTION)
  }

  private def doInsertStatisticsToCollection(collection: MongoCollection[Document],
                                             statistics: Seq[StatisticsInfo]): Boolean = {
    val documents = statistics.map(item => Document(item.toMap))

    logger.info(s"Inserting documents: $documents")
    val insertResult = executeMongoTask(collection.insertMany(documents), MONGO_TIMEOUT)

    if (insertResult.wasAcknowledged) {
      logger.info(s"Documents inserted. _ids: ${insertResult.getInsertedIds}")
      true
    } else {
      logger.warn("Insert was not acknowledged")
      false
    }
  }

  /**
   * @see [[StatisticsService.insertDailyStatistics]]
   */
  override def insertDailyStatistics(statistics: Seq[StatisticsInfo]): Boolean = {
    doInsertStatisticsToCollection(dailyExpenses, statistics)
  }

  /**
   * @see [[StatisticsService.insertMonthlyStatistics]]
   */
  override def insertMonthlyStatistics(statistics: Seq[StatisticsInfo]): Boolean = {
    doInsertStatisticsToCollection(monthlyExpenses, statistics)
  }

  /**
   * @see [[StatisticsService.findStatistics]]
   */
  override def findStatistics(userId: String,
                              year: Int = 2021,
                              month: Option[Int] = None): Option[Seq[StatisticsInfo]] = {
    logger.info(s"Finding statistics. [userId=$userId, year=$year, month=$month]")

    val localStartDate = LocalDateTime.of(year, month.getOrElse(0) + 1, 2, 0, 0, 0, 0)
    var localEndDate = LocalDateTime.from(localStartDate)

    var collection = monthlyExpenses
    if (month.isDefined) {
      localEndDate = localEndDate.plusMonths(1)
      collection = dailyExpenses
    } else {
      localEndDate = localEndDate.plusYears(1)
    }

    val startTime = new Date(localStartDate.toEpochSecond(ZoneOffset.UTC) * 1000)
    val endTime = new Date(localEndDate.toEpochSecond(ZoneOffset.UTC) * 1000)

    val filter = Filters.and(byUserId(userId),
                             Filters.and(Filters.gte(DATE, startTime), Filters.lt(DATE, endTime)))
    val projection = exclude("_id", "__v", CATEGORY_TO_EXPENSES + "._id")
    val documents = executeMongoTask(collection.find(filter)
                                               .projection(projection)
                                               .sort(Sorts.ascending(DATE)),
                                     MONGO_TIMEOUT)

    documentsToModel(documents)
  }

  /**
   * Helper method used to parse expense documents to expense models
   *
   * @param documents The documents to parse
   * @return expense models
   */
  private def documentsToModel(documents: Seq[Document]): Option[Seq[StatisticsInfo]] = {
    if (documents == null) {
      logger.info(s"Expenses could not be found.")
      None
    } else {
      val stringBuilder = new StringBuilder("[")
      stringBuilder.append(documents.map(documentToJson).mkString(","))
      stringBuilder.append("]")
      Json.parse(stringBuilder.toString()).validateOpt[Seq[StatisticsInfo]].get
    }
  }

  /**
   * A mongo filter used to find document by firebaseUserId field
   *
   * @param userId User identifier to find
   * @return A filter to be used by mongo tasks
   */
  private def byUserId(userId: String): Bson = {
    Filters.regex(USER_ID, userId)
  }
}

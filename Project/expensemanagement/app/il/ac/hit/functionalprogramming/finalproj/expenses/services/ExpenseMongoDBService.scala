package il.ac.hit.functionalprogramming.finalproj.expenses.services

import com.mongodb.client.model.Filters
import il.ac.hit.functionalprogramming.finalproj.common.services.MongoService
import il.ac.hit.functionalprogramming.finalproj.common.utils.MongoUtils._
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo._
import org.bson.conversions.Bson
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts
import org.mongodb.scala.{Document, MongoCollection}
import play.api.libs.json.Json
import play.api.{Configuration, Logger}

import java.util.Date
import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.FiniteDuration
import scala.language.postfixOps

/**
 * This class is a service of the data layer which exposes functionality to perform CRUD
 * operations on expenses collection.<br/>
 * It encapsulates the code relevant to MongoDB, such that it is easier to replace it
 * with another injection of [[ExpenseService]].
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
@Singleton
class ExpenseMongoDBService @Inject()(config: Configuration, mongo: MongoService) extends ExpenseService {
  /**
   * Use default time-out of 5 seconds for mongo tasks.
   */
  private val MONGO_TIMEOUT = config.get[FiniteDuration]("mongodb.timeout")

  /**
   * expenses collection name
   */
  private val MONGO_COLLECTION = "expenses"

  private val logger: Logger = Logger(this.getClass)

  /**
   * The expenses Mongo collection. Don't use a val as Play Framework messes up with it during refresh
   *
   * @return expenses collection
   */
  private def expenses: MongoCollection[Document] = {
    mongo.db.getCollection(MONGO_COLLECTION)
  }

  /**
   * @see [[ExpenseService.insertExpense]]
   */
  override def insertExpense(userId: String,
                             sum: Double,
                             currency: String,
                             category: String,
                             description: String,
                             date: Date): Option[ExpenseInfo] = {
    val document = Document(ExpenseInfo(userId, sum, currency, category, description, date).toMap)

    val docAsJson = documentToJson(document)
    logger.info(s"Inserting document: $docAsJson")
    val insertResult = executeMongoTask(expenses.insertOne(document), MONGO_TIMEOUT)

    if (insertResult.wasAcknowledged) {
      logger.info(s"Document inserted: $docAsJson with _id: ${insertResult.getInsertedId}")
      Json.parse(docAsJson).validateOpt[ExpenseInfo].get
    } else {
      logger.warn("Insert was not acknowledged")
      None
    }
  }

  /**
   * @see [[ExpenseService.findExpenses]]
   */
  override def findExpenses(userId: String,
                            page: Int = 0,
                            limit: Int = Int.MaxValue): Option[Seq[ExpenseInfo]] = {
    logger.info(s"Finding expenses. [userId=$userId, page=$page, limit=$limit]")
    val documents = executeMongoTask(expenses.find(byUserId(userId))
                                             .projection(exclude("_id", "__v"))
                                             .sort(Sorts.descending(DATE))
                                             .skip(limit * page)
                                             .limit(limit),
                                     MONGO_TIMEOUT)

    documentsToModel(documents)
  }

  /**
   * @see [[ExpenseService.findAllExpenses]]
   */
  override def findAllExpenses(startTimeMillis: Long = 0,
                               endTimeMillis: Long = System.currentTimeMillis()): Option[Seq[ExpenseInfo]] = {
    val startTime = new Date(startTimeMillis)
    val endTime = new Date(endTimeMillis)

    logger.info(s"Finding expenses. [startTime=$startTime, endTime=$endTime]")
    val filter = Filters.and(Filters.gte(DATE, startTime), Filters.lt(DATE, endTime))
    val documents = executeMongoTask(expenses.find(filter)
                                             .projection(exclude("_id", "__v"))
                                             .sort(Sorts.descending(DATE)),
                                     MONGO_TIMEOUT)

    documentsToModel(documents)
  }

  /**
   * Helper method used to parse expense documents to expense models
   *
   * @param documents The documents to parse
   * @return expense models
   */
  private def documentsToModel(documents: Seq[Document]): Option[Seq[ExpenseInfo]] = {
    if (documents == null) {
      logger.info(s"Expenses could not be found.")
      None
    } else {
      val stringBuilder = new StringBuilder("[")
      stringBuilder.append(documents.map(documentToJson).mkString(","))
      stringBuilder.append("]")
      val value = Json.parse(stringBuilder.toString()).validateOpt[List[ExpenseInfo]]

      if (value.isSuccess) {
        value.get
      } else {
        logger.error(value.toString)
        None
      }
    }
  }

  /**
   * @see [[ExpenseService.deleteExpense]]
   */
  override def deleteExpense(userId: String, expenseId: String): Long = {
    logger.info(s"Deleting document. [userId=$userId, expenseId=$expenseId]")

    // First pull document to make sure it belongs to the specified user
    val deleteResult = executeMongoTask(
      expenses.deleteOne(Filters.and(byUserId(userId), Filters.regex("_id", expenseId))), MONGO_TIMEOUT)

    if (deleteResult.wasAcknowledged) {
      logger.info(s"Deleted count: ${deleteResult.getDeletedCount}")
      deleteResult.getDeletedCount
    } else {
      logger.warn("Delete was not acknowledged")
      0L
    }
  }

  /**
   * @see [[ExpenseService.countExpenses]]
   */
  override def countExpenses(userId: String): Long = {
    executeMongoTask(expenses.countDocuments(byUserId(userId)), MONGO_TIMEOUT)
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

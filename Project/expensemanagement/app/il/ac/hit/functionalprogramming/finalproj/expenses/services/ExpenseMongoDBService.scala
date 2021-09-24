package il.ac.hit.functionalprogramming.finalproj.expenses.services

import com.mongodb.client.model.Filters
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo._
import org.bson.BsonValue
import org.bson.json.{JsonMode, JsonWriterSettings}
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts
import org.mongodb.scala.{Document, MongoCollection, Observable, SingleObservable}
import play.api.libs.json.Json
import play.api.{Configuration, Logger}

import java.util.Date
import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration
import scala.language.postfixOps

/**
 * This class is a service of the data layer which exposes functionality to perform CRUD
 * operations on users collection.<br/>
 * It encapsulates the code relevant to MongoDB, such that it is easier to replace it
 * with another injection of [[UserService]].
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
   * users collection name
   */
  private val MONGO_COLLECTION = "expenses"

  private val logger: Logger = Logger(this.getClass)

  /**
   * The users Mongo collection. Don't use a val as Play Framework messes up with it during refresh
   *
   * @return users collection
   */
  private def expenses: MongoCollection[Document] = mongo.db.getCollection(MONGO_COLLECTION)

  /**
   * Executes a mongo task and wait up to 5 seconds for a response.
   *
   * @param observable The task to wait for
   * @tparam T Type of the response
   * @return The response, as sequence
   */
  private def executeMongoTask[T](observable: Observable[T]): Seq[T] = Await.result(observable.toFuture(), MONGO_TIMEOUT)

  /**
   * Executes a mongo task and wait up to 5 seconds for a response.<br/>
   * Overload for a single expectation
   *
   * @param observable The task to wait for
   * @tparam T Type of the response
   * @return The response
   */
  private def executeMongoTask[T](observable: SingleObservable[T]): T = Await.result(observable.toFuture(), MONGO_TIMEOUT)

  /**
   * A mongo filter used to find document by firebaseUserId field
   *
   * @param userId User identifier to find
   * @return A filter to be used by mongo tasks
   */
  private def byUserId(userId: String) = {
    Filters.regex(USER_ID, userId)
  }

  /**
   * @see [[ExpenseService.insertExpense]]
   */
  override def insertExpense(userId: String,
                             sum: Int,
                             currency: String,
                             category: String,
                             description: String,
                             date: Date): Option[ExpenseInfo] = {
    val document = Document(ExpenseInfo(userId, sum, currency, category, description, date).toMap)

    val docAsJson = documentToJson(document)
    logger.info(s"Inserting document: $docAsJson")
    val insertResult = executeMongoTask(expenses.insertOne(document))

    if (insertResult.wasAcknowledged) {
      logger.info(s"Document inserted: $docAsJson with _id: ${insertResult.getInsertedId}")
      Json.parse(docAsJson).validate[ExpenseInfo].asOpt
    } else {
      logger.warn("Insert was not acknowledged")
      None
    }
  }

  /**
   * @see [[ExpenseService.findExpenses]]
   */
  override def findExpenses(userId: String, page: Int, limit: Int): Option[Seq[ExpenseInfo]] = {
    logger.info(s"Finding user. [userId=$userId]")
    val documents = executeMongoTask(expenses
      .find(byUserId(userId))
      .projection(excludeId())
      .sort(Sorts.descending("date"))
      .skip(limit * page)
      .limit(limit))

    if ((documents == null) || documents.isEmpty) {
      logger.info(s"Expenses could not be found. [userId=$userId, page=$page, limit=$limit]")
      None
    } else {
      val stringBuilder = new StringBuilder("[")
      stringBuilder.append(documents.map(documentToJson).mkString(","))
      stringBuilder.append("]")
      Json.parse(stringBuilder.toString()).validate[Seq[ExpenseInfo]].asOpt
    }
  }

  override def countExpenses(userId: String): Long = {
    executeMongoTask(expenses.countDocuments(byUserId(userId)))
  }

  /**
   * @see [[ExpenseService.deleteExpense]]
   */
  override def deleteExpense(userId: String, expenseId: String): Option[ExpenseInfo] = {
    /*val document: AtomicReference[Option[Document]] = new AtomicReference[Option[Document]](None)
     logger.info(s"Updating document. [userId=$userId, userInfo=$userInfo]")

     // First pull user document to update, so we can copy its existing fields such as _id, etc.
     executeMongoTask(expenses.find(byUserId(userId)).first().map(userDoc => {
       logger.info(s"Document to update: ${userDoc.toJson()}")

       // Collect all properties to update
       val propertiesToUpdate = mutable.Map[String, BsonValue]()
       userDoc.foreach(entry => propertiesToUpdate += (entry._1 -> entry._2))

       // Email is not overridable
       val newValues = userInfo.toMap
       newValues.remove(EMAIL)
       propertiesToUpdate.addAll(newValues)

       // Construct the document and execute update
       document.set(Some(Document(propertiesToUpdate)))
       val updateResult = executeMongoTask(
         expenses.replaceOne(
           byUserId(userId),
           document.get.get,
           new ReplaceOptions().upsert(true)))

       if (updateResult.wasAcknowledged) {
         logger.info(s"Document updated: ${userInfoDocumentToJson(document.get.get)} with _id: ${updateResult.getUpsertedId}")
       } else {
         logger.warn("Update was not acknowledged")
       }

       updateResult
     }))

     if (document.get.isDefined) {
       val doc = docWithExclusions(document.get.get, Set("_id"))
       Json.parse(userInfoDocumentToJson(doc)).validate[UserInfo].asOpt
     } else {
       None
     }*/
    None
  }

  def documentToJson(document: Document): String = {
    // This voodoo was created to overcome the BSONDateTime which parses a date to {"$date": DATE}
    // instead of just giving the DATE. (So we use the SHELL mode)
    // In addition, ISODate literal is unknown by json, so just remove it.
    val docAsJson = document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.SHELL).build())
    docAsJson.replace("ISODate(", "").replace(")", "")
  }

  def docWithExclusions(document: Document, exclusions: Set[String]): Document = {
    val properties = mutable.Map[String, BsonValue]()
    document.filterKeys(!exclusions.contains(_)).foreach(entry => properties += (entry._1 -> entry._2))
    Document(properties)
  }
}

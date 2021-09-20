package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services

import com.mongodb.client.model.{Filters, ReplaceOptions}
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo.{EMAIL, FIREBASE_USER_ID}
import org.bson.BsonValue
import org.mongodb.scala.{Document, MongoCollection, Observable, SingleObservable}
import play.api.libs.json.Json
import play.api.{Configuration, Logger}

import java.util.concurrent.atomic.AtomicReference
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
class UserMongoDBService @Inject()(config: Configuration, mongo: MongoService) extends UserService {
  /**
   * Use default time-out of 5 seconds for mongo tasks.
   */
  private val MONGO_TIMEOUT = config.get[FiniteDuration]("mongodb.timeout")

  /**
   * users collection name
   */
  private val MONGO_COLLECTION = "users"

  private val logger: Logger = Logger(this.getClass)

  /**
   * The users Mongo collection. Don't use a val as Play Framework messes up with it during refresh
   *
   * @return users collection
   */
  private def users: MongoCollection[Document] = mongo.db.getCollection(MONGO_COLLECTION)

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
    Filters.regex(FIREBASE_USER_ID, userId)
  }

  /**
   * @see [[UserService.insertUser]]
   */
  override def insertUser(userId: String, userEmail: String): Option[UserInfo] = {
    val document = Document(FIREBASE_USER_ID -> userId, EMAIL -> userEmail)

    val docAsJson = document.toJson()
    logger.info(s"Inserting document: $docAsJson")
    val insertResult = executeMongoTask(users.insertOne(document))

    if (insertResult.wasAcknowledged) {
      logger.info(s"Document inserted: $docAsJson with _id: ${insertResult.getInsertedId}")
      Json.parse(docAsJson).validate[UserInfo].asOpt
    } else {
      logger.warn("Insert was not acknowledged")
      None
    }
  }

  /**
   * @see [[UserService.findUser]]
   */
  override def findUser(userId: String): Option[UserInfo] = {
    logger.info(s"Finding user. [userId=$userId]")
    val document = executeMongoTask(users.find(byUserId(userId)).first())

    if ((document == null) || document.isEmpty) {
      logger.info(s"User could not be found. [userId=$userId]")
      None
    } else {
      val docAsJson = document.toJson()
      logger.info(s"User found: $docAsJson")
      Json.parse(docAsJson).validate[UserInfo].asOpt
    }
  }

  /**
   * @see [[UserService.updateUser]]
   */
  override def updateUser(userId: String, userInfo: UserInfo): Option[UserInfo] = {
    val document: AtomicReference[Option[Document]] = new AtomicReference[Option[Document]](None)
    logger.info(s"Updating document. [userId=$userId, userInfo=$userInfo]")

    // First pull user document to update, so we can copy its existing fields such as _id, etc.
    executeMongoTask(users.find(byUserId(userId)).first().map(userDoc => {
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
        users.replaceOne(
          byUserId(userId),
          document.get.get,
          new ReplaceOptions().upsert(true)))

      if (updateResult.wasAcknowledged) {
        logger.info(s"Document updated: ${document.get.get.toJson()} with _id: ${updateResult.getUpsertedId}")
      } else {
        logger.warn("Update was not acknowledged")
      }

      updateResult
    }))

    if (document.get.isDefined) {
      Json.parse(document.get.get.toJson()).validate[UserInfo].asOpt
    } else {
      None
    }
  }
}

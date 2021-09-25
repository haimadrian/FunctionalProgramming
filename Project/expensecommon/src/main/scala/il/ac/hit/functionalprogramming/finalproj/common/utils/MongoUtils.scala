package il.ac.hit.functionalprogramming.finalproj.common.utils

import org.bson.BsonValue
import org.bson.json.{JsonMode, JsonWriterSettings}
import org.mongodb.scala.{Document, Observable, SingleObservable}

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration

/** An object that exposes utilities for Json and [[Document]], to support our customized conversion
 * as we cannot control the BSONDateTime object and flat its value when marshalling to Json.
 *
 * @author Haim Adrian
 * @since 24 Sep 2021
 */
object MongoUtils {

  /** Executes a mongo task and wait up to timeout for a response.
   *
   * @param observable The task to wait for
   * @tparam T Type of the response
   * @return The response, as sequence
   */
  def executeMongoTask[T](observable: Observable[T], timeout: FiniteDuration): Seq[T] = {
    Await.result(observable.toFuture(), timeout)
  }

  /** Executes a mongo task and wait up to timeout for a response.<br/>
   * Overload for a single expectation
   *
   * @param observable The task to wait for
   * @tparam T Type of the response
   * @return The response
   */
  def executeMongoTask[T](observable: SingleObservable[T], timeout: FiniteDuration): T = {
    Await.result(observable.toFuture(), timeout)
  }

  /** This voodoo was created to overcome the BSONDateTime which parses a date to {"$date": DATE}
   * instead of just giving the DATE. (So we use the SHELL mode)<br/>
   * In addition, ISODate literal is unknown by json, so just remove it and leave the json with
   * normal date string..
   *
   * @param document A document to convert to Json string
   * @return A Json string representing the specified document
   */
  def documentToJson(document: Document): String = {
    val docAsJson = document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.SHELL).build())
    docAsJson.replace("ISODate(", "").replace(")", "")
  }

  /** A method used to take an immutable document and return a new document with values that
   * do not appear in exclusions.
   *
   * @param document   The document to copy
   * @param exclusions Set of keys to remove from the specified document
   * @return A copy of the specified document without the keys passed through exclusions
   */
  def docWithExclusions(document: Document, exclusions: Set[String]): Document = {
    val properties = mutable.Map[String, BsonValue]()
    document.filterKeys(!exclusions.contains(_)).foreach(entry => properties += (entry._1 -> entry._2))
    Document(properties)
  }
}

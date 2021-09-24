package il.ac.hit.functionalprogramming.finalproj.expenses.services

import org.mongodb.scala._
import play.api.{Configuration, Logger}

import javax.inject.{Inject, Singleton}

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
@Singleton
class MongoService @Inject()(config: Configuration) {
  private val logger: Logger = Logger(this.getClass)

  val db: MongoDatabase = connect()

  private def connect() = {
    logger.info("Connecting to MongoDB")
    System.setProperty("org.mongodb.async.type", "netty")

    val client: MongoClient = MongoClient(config.get[String]("mongodb.uri"))
    client.getDatabase("expense_manager")
  }
}

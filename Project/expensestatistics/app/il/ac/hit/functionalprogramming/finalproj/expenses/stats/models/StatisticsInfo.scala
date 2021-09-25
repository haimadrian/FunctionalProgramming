package il.ac.hit.functionalprogramming.finalproj.expenses.stats.models

import il.ac.hit.functionalprogramming.finalproj.expenses.stats.models.StatisticsInfo._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonArray, BsonDateTime, BsonDocument, BsonDouble, BsonString, Document}
import play.api.libs.json.Reads.dateReads
import play.api.libs.json.Writes.dateWrites
import play.api.libs.json.{Format, Json, Reads, Writes}

import java.util.Date

/**
 * @author Orel Gershonovich
 * @since 23-Sep-2021
 */
case class StatisticsInfo(userId: String,
                          date: Date = new Date(),
                          totalExpenses: Double = 0,
                          categoryToExpenses: List[CategoryToExpenses] = List()) {

  def toMap: Map[String, BsonValue] = {
    Map[String, BsonValue](
      USER_ID -> BsonString(userId),
      DATE -> BsonDateTime(date),
      TOTAL_EXPENSES -> BsonDouble(totalExpenses),
      CATEGORY_TO_EXPENSES -> BsonArray.fromIterable(categoryToExpenses.map(item => {
        BsonDocument(Document(item.toMap).toJson())
      }))
      )
  }
}

object StatisticsInfo {
  private val ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  implicit val customDateWrites: Writes[java.util.Date] = dateWrites(ISO8601_DATE_FORMAT)
  implicit val customDateReads: Reads[java.util.Date] = dateReads(ISO8601_DATE_FORMAT)
  implicit val statisticsInfoJsonFormat: Format[StatisticsInfo] = Json
    .using[Json.WithDefaultValues]
    .format[StatisticsInfo]

  val USER_ID = "userId"
  val DATE = "date"
  val TOTAL_EXPENSES = "totalExpenses"
  val CATEGORY_TO_EXPENSES = "categoryToExpenses"

  def apply(userId: String,
            date: Date,
            totalExpenses: Double,
            categoryToExpenses: List[CategoryToExpenses]): StatisticsInfo = {
    new StatisticsInfo(userId, date, totalExpenses, categoryToExpenses)
  }

  def apply(document: Document): StatisticsInfo = {
    val categoryToExpensesDoc = document.getList[Document](CATEGORY_TO_EXPENSES, classOf[Document])
    val categoryToExpenses = Array[CategoryToExpenses]()
    categoryToExpensesDoc.stream().forEach(item => categoryToExpenses :+ CategoryToExpenses(item))

    new StatisticsInfo(document.getString(USER_ID),
                       document.getDate(DATE),
                       document.getDouble(TOTAL_EXPENSES),
                       categoryToExpenses.toList)
  }
}


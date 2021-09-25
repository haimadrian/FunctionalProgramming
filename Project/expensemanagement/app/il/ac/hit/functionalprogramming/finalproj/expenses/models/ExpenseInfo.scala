package il.ac.hit.functionalprogramming.finalproj.expenses.models

import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonDateTime, BsonDouble, BsonString, Document}
import play.api.libs.json.Reads.dateReads
import play.api.libs.json.Writes.dateWrites
import play.api.libs.json.{Format, Json, Reads, Writes}

import java.util.Date

/**
 * @author Orel Gershonovich
 * @since 23-Sep-2021
 */
case class ExpenseInfo(userId: String,
                       sum: Double = 0,
                       currency: String = "",
                       category: String = "",
                       description: String = "",
                       date: Date = new Date()) {

  def toMap: Map[String, BsonValue] = {
    Map[String, BsonValue](
      USER_ID -> BsonString(userId),
      SUM -> BsonDouble(sum),
      CURRENCY -> BsonString(currency),
      CATEGORY -> BsonString(category),
      DESCRIPTION -> BsonString(description),
      DATE -> BsonDateTime(date)
      )
  }
}

object ExpenseInfo {
  private val ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  implicit val customDateWrites: Writes[java.util.Date] = dateWrites(ISO8601_DATE_FORMAT)
  implicit val customDateReads: Reads[java.util.Date] = dateReads(ISO8601_DATE_FORMAT)
  implicit val expenseInfoJsonFormat: Format[ExpenseInfo] = Json.using[Json.WithDefaultValues].format[ExpenseInfo]

  val USER_ID = "userId"
  val SUM = "sum"
  val CURRENCY = "currency"
  val CATEGORY = "category"
  val DESCRIPTION = "description"
  val DATE = "date"

  def apply(userId: String,
            sum: Double,
            currency: String,
            category: String,
            description: String,
            date: Date): ExpenseInfo = {
    new ExpenseInfo(userId, sum, currency, category, description, date)
  }

  def apply(document: Document): ExpenseInfo = {
    new ExpenseInfo(document.getString(USER_ID),
                    document.getDouble(SUM),
                    document.getString(CURRENCY),
                    document.getString(CATEGORY),
                    document.getString(DESCRIPTION),
                    document.getDate(DATE))
  }
}





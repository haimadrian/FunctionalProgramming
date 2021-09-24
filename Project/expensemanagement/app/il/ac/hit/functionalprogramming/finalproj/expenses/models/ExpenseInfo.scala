package il.ac.hit.functionalprogramming.finalproj.expenses.models

import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonDateTime, BsonInt32, BsonString, Document}
import play.api.libs.json.{Format, Json}

import java.util.Date

/**
 * @author Orel Gershonovich
 * @since 23-Sep-2021
 */
case class ExpenseInfo(userId: String,
                       sum: Int = 0,
                       currency: String = "",
                       category: String = "",
                       description: String = "",
                       date: Date = new Date()) {

  def toMap: Map[String, BsonValue] = {
    Map[String, BsonValue](
      USER_ID -> BsonString(userId),
      SUM -> BsonInt32(sum),
      CURRENCY -> BsonString(currency),
      CATEGORY -> BsonString(category),
      DESCRIPTION -> BsonString(description),
      DATE -> BsonDateTime(date)
    )
  }
}

object ExpenseInfo {
  implicit val expenseInfoJsonFormat: Format[ExpenseInfo] = Json.using[Json.WithDefaultValues].format[ExpenseInfo]

  val USER_ID = "userId"
  val SUM = "sum"
  val CURRENCY = "currency"
  val CATEGORY = "category"
  val DESCRIPTION = "description"
  val DATE = "date"

  def apply(userId: String,
            sum: Int,
            currency: String,
            category: String,
            description: String,
            date: Date): ExpenseInfo = {
    new ExpenseInfo(userId, sum, currency, category, description, date)
  }

  def apply(document: Document): ExpenseInfo = {
    new ExpenseInfo(document.getString(USER_ID),
      document.getInteger(SUM),
      document.getString(CURRENCY),
      document.getString(CATEGORY),
      document.getString(DESCRIPTION),
      document.getDate(DATE))
  }
}





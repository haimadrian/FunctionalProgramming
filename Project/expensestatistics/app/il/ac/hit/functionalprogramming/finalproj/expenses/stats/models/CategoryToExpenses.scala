package il.ac.hit.functionalprogramming.finalproj.expenses.stats.models

import il.ac.hit.functionalprogramming.finalproj.expenses.stats.models.CategoryToExpenses._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonDouble, BsonString, Document}
import play.api.libs.json.{Format, Json}

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class CategoryToExpenses(category: String = "", totalExpenses: Double = 0) {

  def toMap: Map[String, BsonValue] = {
    Map(
      CATEGORY -> BsonString(category),
      TOTAL_EXPENSES -> BsonDouble(totalExpenses)
      )
  }
}

object CategoryToExpenses {
  implicit val categoryToExpensesJsonFormat: Format[CategoryToExpenses] =
    Json.using[Json.WithDefaultValues].format[CategoryToExpenses]

  val CATEGORY = "category"
  val TOTAL_EXPENSES = "totalExpenses"

  def apply(category: String, totalExpenses: Double): CategoryToExpenses = {
    new CategoryToExpenses(category, totalExpenses)
  }

  def apply(document: Document): CategoryToExpenses = {
    new CategoryToExpenses(document.getString(CATEGORY), document.getDouble(TOTAL_EXPENSES))
  }
}

package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.Address._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonInt32, BsonString, Document}
import play.api.libs.json.{Format, Json}

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class Address(street: String = "",
                   city: String = "",
                   state: String = "",
                   postalCode: Int = 0,
                   country: String = "") {

  def toMap: Map[String, BsonValue] = {
    Map(
      STREET -> BsonString(street),
      CITY -> BsonString(city),
      STATE -> BsonString(state),
      POSTAL_CODE -> BsonInt32(postalCode),
      COUNTRY -> BsonString(country)
      )
  }
}

object Address {
  implicit val addressJsonFormat: Format[Address] = Json.using[Json.WithDefaultValues].format[Address]

  val STREET = "street"
  val CITY = "city"
  val STATE = "state"
  val POSTAL_CODE = "postalCode"
  val COUNTRY = "country"

  def apply(street: String,
            city: String,
            state: String,
            postalCode: Int,
            country: String): Address = {
    new Address(street, city, state, postalCode, country)
  }

  def apply(document: Document): Address = {
    new Address(document.getString(STREET),
                document.getString(CITY),
                document.getString(STATE),
                document.getInteger(POSTAL_CODE),
                document.getString(COUNTRY))
  }
}

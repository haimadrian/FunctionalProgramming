package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonDateTime, BsonDocument, BsonString, Document}
import play.api.libs.json.{Format, JsPath, Json, Reads, Writes}
import play.api.libs.functional.syntax._

import java.util.Date
import scala.collection.mutable

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class UserInfo(email: String,
                    firstName: String = "",
                    lastName: String = "",
                    phoneNumber: String = "",
                    dateOfBirth: String = new Date().toString,
                    address: Address = new Address,
                    maritalStatus: String = "SINGLE") {

  def toMap: mutable.Map[String, BsonValue] = {
    val map = mutable.Map[String, BsonValue](
      EMAIL -> BsonString(email),
      FIRST_NAME -> BsonString(firstName),
      LAST_NAME -> BsonString(lastName),
      PHONE_NUMBER -> BsonString(phoneNumber),
      MARITAL_STATUS -> BsonString(maritalStatus),
      DATE_OF_BIRTH -> BsonString(dateOfBirth)
    )

    if (address != null) {
      map += (ADDRESS -> BsonDocument(Document(address.toMap).toJson()))
    }

    map
  }
}

object UserInfo {
  implicit val userInfoJsonFormat: Format[UserInfo] = Json.using[Json.WithDefaultValues].format[UserInfo]

  val EMAIL = "email"
  val FIRST_NAME = "firstName"
  val LAST_NAME = "lastName"
  val PHONE_NUMBER = "phoneNumber"
  val DATE_OF_BIRTH = "dateOfBirth"
  val ADDRESS = "address"
  val MARITAL_STATUS = "maritalStatus"
  val FIREBASE_USER_ID = "firebaseUserId"

  /*implicit val userInfoWrites: Writes[UserInfo] = (
    (JsPath \ EMAIL).write[String] and
      (JsPath \ FIRST_NAME).write[String] and
      (JsPath \ LAST_NAME).write[String] and
      (JsPath \ PHONE_NUMBER).write[String] and
      (JsPath \ DATE_OF_BIRTH).write[String] and
      (JsPath \ ADDRESS).write[Address] and
      (JsPath \ MARITAL_STATUS).write[String]
    ) (unlift(UserInfo.unapply))

  implicit val userInfoReads: Reads[UserInfo] = (
    (JsPath \ EMAIL).read[String] and
      (JsPath \ FIRST_NAME).read[String] and
      (JsPath \ LAST_NAME).read[String] and
      (JsPath \ PHONE_NUMBER).read[String] and
      (JsPath \ DATE_OF_BIRTH).read[String] and
      (JsPath \ ADDRESS).read[Address] and
      (JsPath \ MARITAL_STATUS).read[String]
    )(UserInfo.apply(_, _, _, _, _, _, _))*/

  def apply(email: String,
            firstName: String,
            lastName: String,
            phoneNumber: String,
            dateOfBirth: String,
            address: Address,
            maritalStatus: String): UserInfo = {
    new UserInfo(email, firstName, lastName, phoneNumber, dateOfBirth, address, maritalStatus)
  }

  def apply(document: Document): UserInfo = {
    new UserInfo(document.getString(EMAIL),
      document.getString(FIRST_NAME),
      document.getString(LAST_NAME),
      document.getString(PHONE_NUMBER),
      document.getString(DATE_OF_BIRTH),
      Address(document.getOrElse(ADDRESS, BsonDocument()).asDocument()),
      document.getString(MARITAL_STATUS))
  }
}



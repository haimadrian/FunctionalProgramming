package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo._
import org.bson.BsonValue
import org.mongodb.scala.bson.{BsonDateTime, BsonDocument, BsonString, Document}
import play.api.libs.json.Reads.dateReads
import play.api.libs.json.Writes.dateWrites
import play.api.libs.json.{Format, Json, Reads, Writes}

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
                    dateOfBirth: Date = new Date(),
                    address: Address = new Address,
                    maritalStatus: String = "SINGLE") {

  def toMap: mutable.Map[String, BsonValue] = {
    val map = mutable.Map[String, BsonValue](
      EMAIL -> BsonString(email),
      FIRST_NAME -> BsonString(firstName),
      LAST_NAME -> BsonString(lastName),
      PHONE_NUMBER -> BsonString(phoneNumber),
      MARITAL_STATUS -> BsonString(maritalStatus),
      DATE_OF_BIRTH -> BsonDateTime(dateOfBirth)
      )

    if (address != null) {
      map += (ADDRESS -> BsonDocument(Document(address.toMap).toJson()))
    }

    map
  }
}

object UserInfo {
  private val ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  implicit val customDateWrites: Writes[java.util.Date] = dateWrites(ISO8601_DATE_FORMAT)
  implicit val customDateReads: Reads[java.util.Date] = dateReads(ISO8601_DATE_FORMAT)
  implicit val userInfoJsonFormat: Format[UserInfo] = Json.using[Json.WithDefaultValues].format[UserInfo]

  val EMAIL = "email"
  val FIRST_NAME = "firstName"
  val LAST_NAME = "lastName"
  val PHONE_NUMBER = "phoneNumber"
  val DATE_OF_BIRTH = "dateOfBirth"
  val ADDRESS = "address"
  val MARITAL_STATUS = "maritalStatus"
  val FIREBASE_USER_ID = "firebaseUserId"

  def apply(email: String,
            firstName: String = "",
            lastName: String = "",
            phoneNumber: String = "",
            dateOfBirth: Date = new Date(),
            address: Address = new Address,
            maritalStatus: String = "SINGLE"): UserInfo = {
    new UserInfo(email, firstName, lastName, phoneNumber, dateOfBirth, address, maritalStatus)
  }

  def apply(document: Document): UserInfo = {
    new UserInfo(document.getString(EMAIL),
                 document.getString(FIRST_NAME),
                 document.getString(LAST_NAME),
                 document.getString(PHONE_NUMBER),
                 document.getDate(DATE_OF_BIRTH),
                 Address(document.getOrElse(ADDRESS, BsonDocument()).asDocument()),
                 document.getString(MARITAL_STATUS))
  }
}



package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models

import play.api.libs.json.{Format, Json}

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class UserAuthInfo(userId: String, userEmail: String, jwt: String) {

}

object UserAuthInfo {
  implicit val userAuthInfoJsonFormat: Format[UserAuthInfo] = Json.format[UserAuthInfo]

  def apply(userId: String, userEmail: String, jwt: String): UserAuthInfo = {
    new UserAuthInfo(userId, userEmail, jwt)
  }
}

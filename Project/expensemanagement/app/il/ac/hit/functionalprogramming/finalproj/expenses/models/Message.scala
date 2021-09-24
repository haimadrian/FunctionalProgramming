package il.ac.hit.functionalprogramming.finalproj.expenses.models

import play.api.libs.json.{Format, Json}

/**
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class Message(message: String) {

}

object Message {
  implicit val errorJsonFormat: Format[Message] = Json.format[Message]

  def apply(message: String): Message = {
    new Message(message)
  }
}

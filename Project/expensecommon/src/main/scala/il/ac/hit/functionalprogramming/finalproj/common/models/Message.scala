package il.ac.hit.functionalprogramming.finalproj.common.models

import play.api.libs.json.{Format, Json}

/** Represents a simple Message model to return to client as JSON. ({"message": "some message"})
 *
 * @author Haim Adrian
 * @since 19 Sep 2021
 */
case class Message(message: String)

object Message {
  implicit val messageJsonFormat: Format[Message] = Json.format[Message]

  def apply(message: String): Message = {
    new Message(message)
  }
}

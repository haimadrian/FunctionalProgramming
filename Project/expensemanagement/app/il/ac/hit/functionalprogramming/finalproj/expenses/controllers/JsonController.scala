package il.ac.hit.functionalprogramming.finalproj.expenses.controllers

import play.api.libs.json.{Json, Writes}
import play.api.mvc._

/**
 * A controller that handles body of an HTTP req/res as json.
 */
abstract class JsonController(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Generates a ‘200 OK’ result.
   *
   * @param body      The body to write as json
   * @param writeable A [[Writes]] defining how to write the specified body to json
   * @tparam BodyType Type of the body
   * @return an OK result
   */
  def retOk[BodyType](body: BodyType)(implicit writeable: Writes[BodyType]): Result = {
    Ok(Json.toJson(body))
  }

  /**
   * Generates a ‘400 Bad Request’ result.
   *
   * @param body      The body to write as json
   * @param writeable A [[Writes]] defining how to write the specified body to json
   * @tparam BodyType Type of the body
   * @return a bad request result
   */
  def retBadRequest[BodyType](body: BodyType)(implicit writeable: Writes[BodyType]): Result = {
    BadRequest(Json.toJson(body))
  }

  /**
   * Generates a ‘404 Not Found’ result.
   *
   * @param body      The body to write as json
   * @param writeable A [[Writes]] defining how to write the specified body to json
   * @tparam BodyType Type of the body
   * @return a bad request result
   */
  def retNotFound[BodyType](body: BodyType)(implicit writeable: Writes[BodyType]): Result = {
    NotFound(Json.toJson(body))
  }

  /**
   * Generates a ‘500 Server Error’ result.
   *
   * @param body      The body to write as json
   * @param writeable A [[Writes]] defining how to write the specified body to json
   * @tparam BodyType Type of the body
   * @return a server error result
   */
  def retServerError[BodyType](body: BodyType)(implicit writeable: Writes[BodyType]): Result = {
    InternalServerError(Json.toJson(body))
  }

}

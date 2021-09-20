package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserAuthInfo
import play.api.libs.json.JsValue
import play.api.mvc._

import javax.inject._

/**
 * This controller created so other micro services of expense management application will be able to
 * authorize users at their backend.
 */
@Singleton
class AuthorizationController @Inject()(auth: SessionAction, cc: ControllerComponents)
  extends JsonController(cc) {

  /**
   * An action responsible for authorizing a request.<br/>
   * This action is exposed so our other micros services can authorize users.
   */
  def auth: Action[JsValue] = auth(parse.json) { request: SessionRequest[_] =>
    retOk(UserAuthInfo(request.userId, request.email, ""))
  }

}

package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.{Message, UserInfo}
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services.{UserAuthCache, UserMongoDBService}
import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

/**
 * This controller serves user requests, such as sign in, sign out, and info.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents, auth: SessionAction, userService: UserMongoDBService)
  extends JsonController(cc) {

  /**
   * The /api/signup PUT service stores user's firebase identifier and email in the users collection
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 500 ServerError upon failure)
   */
  def signup: Action[JsValue] = auth(parse.json) { request: SessionRequest[_] =>
    val maybeInfo = userService.insertUser(request.userId, request.email)

    if (maybeInfo.isDefined) {
      retOk(maybeInfo.get)
    } else {
      retServerError(Message("Failed to save sign-up info"))
    }
  }

  /**
   * The /api/signout POST service remove user details from cache, so we will re-authorize it next time
   *
   * @return [[play.api.mvc.Result]] (200 OK)
   */
  def signout: Action[AnyContent] = auth { request: SessionRequest[_] =>
    UserAuthCache.remove(request.jwt)
    retOk(Message("Signed Out"))
  }

  /**
   * The /api/user/info GET service pulls user information from mongo and return it as json to caller
   *
   * @return [[play.api.mvc.Result]] (200 OK with user info upon success, 404 NotFound if user does not exist)
   */
  def getInfo: Action[AnyContent] = auth { implicit request: SessionRequest[_] =>
    val maybeInfo = userService.findUser(request.userId)

    if (maybeInfo.isDefined) {
      retOk(maybeInfo.get)
    } else {
      retNotFound(Message("User does not exist"))
    }
  }

  /**
   * The /api/user/info POST service stores all specified user fields in the users collection.<br/>
   * Note that email field is not modifiable, hence it will be ignored if specified.
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 400 BadRequest /
   *         500 ServerError upon failure)
   */
  def postInfo: Action[JsValue] = auth(parse.json) { implicit request: SessionRequest[JsValue] =>
    var errorMessage: Option[String] = None
    var maybeInfo: Option[UserInfo] = None

    try {
      val userInfo = request.body.validate[UserInfo]
      if (userInfo.isSuccess) {
        maybeInfo = userService.updateUser(request.userId, userInfo.get)
      } else {
        errorMessage = Some("Bad body for user info update")
      }
    } catch {
      case e: Throwable => errorMessage = Some(e.getMessage)
    }

    if (maybeInfo.isDefined) {
      retOk(maybeInfo.get)
    } else if (errorMessage.isDefined) {
      retBadRequest(Message(errorMessage.get))
    } else {
      retServerError(Message("Unknown error has occurred during update"))
    }
  }

}

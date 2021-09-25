package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import il.ac.hit.functionalprogramming.finalproj.common.controllers.{JsonController, SessionRequest}
import il.ac.hit.functionalprogramming.finalproj.common.models.Message
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.middlewares.SessionAction
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services.{UserAuthCache, UserService}
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc.{Action, AnyContent, ControllerComponents, Result}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

/**
 * This controller serves user requests, such as sign in, sign out, and info.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents, auth: SessionAction, userService: UserService)
  extends JsonController(cc) {

  private val logger: Logger = Logger(this.getClass)

  /**
   * The /api/signup PUT service stores user's firebase identifier and email in the users collection
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 500 ServerError upon failure)
   */
  def signup: Action[JsValue] = {
    auth(parse.json) { request: SessionRequest[_] =>
      try {
        val maybeInfo = userService.insertUser(request.userId, request.email)

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retServerError(Message("Failed to save sign-up info"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * A utility wrapper for internal server error, to return server error response and print the error to log
   *
   * @param thrown The internal server error
   */
  private def handleServerError(thrown: Throwable): Result = {
    logger.error("Error has occurred:", thrown)
    retServerError(Message(thrown.getMessage))
  }

  /**
   * The /api/signout POST service remove user details from cache, so we will re-authorize it next time
   *
   * @return [[play.api.mvc.Result]] (200 OK)
   */
  def signout: Action[AnyContent] = {
    auth { request: SessionRequest[_] =>
      try {
        UserAuthCache.remove(request.jwt)
        retOk(Message("Signed Out"))
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/user/info GET service pulls user information from mongo and return it as json to caller
   *
   * @return [[play.api.mvc.Result]] (200 OK with user info upon success, 404 NotFound if user does not exist)
   */
  def getInfo: Action[AnyContent] = {
    auth { implicit request: SessionRequest[_] =>
      try {
        val maybeInfo = userService.findUser(request.userId)

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retNotFound(Message("User does not exist"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/user/info POST service stores all specified user fields in the users collection.<br/>
   * Note that email field is not modifiable, hence it will be ignored if specified.
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 400 BadRequest /
   *         500 ServerError upon failure)
   */
  def postInfo: Action[JsValue] = {
    auth(parse.json) { implicit request: SessionRequest[JsValue] =>
      try {
        val userInfo = request.body.validate[UserInfo]
        if (userInfo.isSuccess) {
          val maybeInfo = userService.updateUser(request.userId, userInfo.get)
          if (maybeInfo.isDefined) {
            retOk(maybeInfo.get)
          } else {
            retServerError(Message("Unknown error has occurred during update"))
          }
        } else {
          retBadRequest(Message("Bad body for user info update"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }
}

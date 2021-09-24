package il.ac.hit.functionalprogramming.finalproj.expenses.controllers

import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo.USER_ID
import il.ac.hit.functionalprogramming.finalproj.expenses.models.{ExpenseInfo, Message}
import il.ac.hit.functionalprogramming.finalproj.expenses.services.ExpenseService
import play.api.libs.json.{JsObject, JsString, JsValue}
import play.api.mvc.{Action, AnyContent, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

/**
 * This controller serves user requests, such as sign in, sign out, and info.
 */
@Singleton
class ExpenseController @Inject()(cc: ControllerComponents, auth: SessionAction, expenseService: ExpenseService)
  extends JsonController(cc) {

  /**
   * The /api/signup PUT service stores user's firebase identifier and email in the users collection
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 500 ServerError upon failure)
   */
  def postExpense(): Action[JsValue] = auth(parse.json) { request: SessionRequest[JsValue] =>
    var errorMessage: Option[String] = None
    var maybeInfo: Option[ExpenseInfo] = None

    try {
      val extendedObject = request.body.as[JsObject].++(new JsObject(Map(USER_ID -> JsString(request.userId))))
      val expenseInfoJsResult = extendedObject.validate[ExpenseInfo]
      if (expenseInfoJsResult.isSuccess) {
        val expenseInfo = expenseInfoJsResult.get
        maybeInfo = expenseService.insertExpense(request.userId,
          expenseInfo.sum,
          expenseInfo.currency,
          expenseInfo.category,
          expenseInfo.description,
          expenseInfo.date)
      } else {
        errorMessage = Some("Bad body for expense info update")
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

  /**
   * The /api/signout POST service remove user details from cache, so we will re-authorize it next time
   *
   * @return [[play.api.mvc.Result]] (200 OK)
   */
  def deleteExpense(): Action[AnyContent] = auth { request: SessionRequest[_] =>
    //UserAuthCache.remove(request.jwt)
    retOk(Message("Signed Out"))
  }

  /**
   * The /api/user/info GET service pulls user information from mongo and return it as json to caller
   *
   * @return [[play.api.mvc.Result]] (200 OK with user info upon success, 404 NotFound if user does not exist)
   */
  def fetchExpenses(page: Int, limit: Int): Action[AnyContent] = auth { implicit request: SessionRequest[_] =>
    var errorMessage: Option[String] = None
    var maybeInfo: Option[Seq[ExpenseInfo]] = None

    try {
      maybeInfo = expenseService.findExpenses(request.userId, page, limit)
    } catch {
      case e: Throwable => errorMessage = Some(e.getMessage)
    }

    if (maybeInfo.isDefined) {
      retOk(maybeInfo.get)
    } else if (errorMessage.isDefined) {
      retBadRequest(Message(errorMessage.get))
    } else {
      retNotFound(Message("No expenses"))
    }
  }

  /**
   * The /api/user/info POST service stores all specified user fields in the users collection.<br/>
   * Note that email field is not modifiable, hence it will be ignored if specified.
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 400 BadRequest /
   *         500 ServerError upon failure)
   */
  def countExpenses: Action[AnyContent] = auth { implicit request: SessionRequest[_] =>
    var errorMessage: Option[String] = None

    var count = 0L
    try {
      count = expenseService.countExpenses(request.userId)
    } catch {
      case e: Throwable => errorMessage = Some(e.getMessage)
    }

    if (errorMessage.isDefined) {
      retBadRequest(Message(errorMessage.get))
    } else {
      retOk(count)
    }
  }

}

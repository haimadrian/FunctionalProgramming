package il.ac.hit.functionalprogramming.finalproj.expenses.controllers

import il.ac.hit.functionalprogramming.finalproj.common.controllers.{JsonController, SessionRequest}
import il.ac.hit.functionalprogramming.finalproj.common.models.Message
import il.ac.hit.functionalprogramming.finalproj.expenses.middlewares.{AdminSessionAction, SessionAction}
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo
import il.ac.hit.functionalprogramming.finalproj.expenses.models.ExpenseInfo.USER_ID
import il.ac.hit.functionalprogramming.finalproj.expenses.services.ExpenseService
import play.api.Logger
import play.api.libs.json.{JsObject, JsString, JsValue}
import play.api.mvc.{Action, AnyContent, ControllerComponents, Result}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

/**
 * This controller serves expense requests, such as fetch, count, delete, etc.
 */
@Singleton
class ExpenseController @Inject()(cc: ControllerComponents,
                                  auth: SessionAction,
                                  authAdmin: AdminSessionAction,
                                  expenseService: ExpenseService)
  extends JsonController(cc) {

  private val logger: Logger = Logger(this.getClass)

  /**
   * The /api/expense POST service stores expense info to mongo
   *
   * @return [[play.api.mvc.Result]] (200 OK with document data upon success, 500 ServerError upon failure)
   */
  def postExpense(): Action[JsValue] = {
    auth(parse.json) { request: SessionRequest[JsValue] =>
      try {
        val extendedObject = request.body.as[JsObject] ++ new JsObject(Map(USER_ID -> JsString(request
                                                                                                 .userId)))
        val expenseInfoJsResult = extendedObject.validateOpt[ExpenseInfo]
        if (expenseInfoJsResult.isSuccess) {
          val expenseInfo = expenseInfoJsResult.get.get
          val maybeInfo = expenseService.insertExpense(request.userId,
                                                       expenseInfo.sum,
                                                       expenseInfo.currency,
                                                       expenseInfo.category,
                                                       expenseInfo.description,
                                                       expenseInfo.date)

          if (maybeInfo.isDefined) {
            retOk(maybeInfo.get)
          } else {
            retServerError(Message("Unknown error has occurred during creation"))
          }
        } else {
          retBadRequest(Message("Bad body for expense info creation"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/expense DELETE service remove expense details from mongo
   *
   * @return [[play.api.mvc.Result]] (200 OK with document count (1/0) upon success, 500 ServerError upon
   *         failure)
   */
  def deleteExpense(): Action[JsValue] = {
    auth(parse.json) { request: SessionRequest[JsValue] =>
      try {
        val count =
          expenseService.deleteExpense(request.userId,
                                       request.body.as[JsObject].value("expenseId").as[JsString].value)
        retOk(count)
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/expense/fetch/page/:page/limit/:limit GET service pulls expenses at the specified
   * page with the specified limit, and return to client (for pagination)
   *
   * @return [[play.api.mvc.Result]] (200 OK with expenses info upon success, 404 NotFound if expenses do
   *         not exist)
   */
  def fetchExpenses(page: Int, limit: Int): Action[AnyContent] = {
    auth { implicit request: SessionRequest[_] =>
      try {
        val maybeInfo = expenseService.findExpenses(request.userId, page, limit)

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retNotFound(Message("No expenses could be found"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/expense/fetch/all/start/:start/end/:end GET service pulls expenses of all users at
   * some specified time range.<br/>
   * It is used by statistics micro-service, to compute statistics for users.
   *
   * @return [[play.api.mvc.Result]] (200 OK with expenses info upon success, 500 ServerError if failed)
   */
  def fetchAllExpenses(start: Long, end: Long): Action[AnyContent] = {
    authAdmin { implicit request: SessionRequest[_] =>
      try {
        val maybeInfo = expenseService.findAllExpenses(start, end)

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retNotFound(Message("No expenses could be found"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/expense/count GET service will return how many expenses there are for the requesting user.<br/>
   * This service is needed for client to calculate amount of pages for pagination.
   *
   * @return [[play.api.mvc.Result]] (200 OK with count upon success, 500 ServerError upon failure)
   */
  def countExpenses: Action[AnyContent] = {
    auth { implicit request: SessionRequest[_] =>
      try {
        val count = expenseService.countExpenses(request.userId)
        retOk(count)
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
}

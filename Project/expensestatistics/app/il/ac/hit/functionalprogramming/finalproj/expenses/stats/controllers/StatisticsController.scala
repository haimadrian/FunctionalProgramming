package il.ac.hit.functionalprogramming.finalproj.expenses.stats.controllers

import il.ac.hit.functionalprogramming.finalproj.common.controllers.{JsonController, SessionRequest}
import il.ac.hit.functionalprogramming.finalproj.common.models.Message
import il.ac.hit.functionalprogramming.finalproj.expenses.stats.middlewares.SessionAction
import il.ac.hit.functionalprogramming.finalproj.expenses.stats.services.StatisticsService
import play.api.Logger
import play.api.mvc.{Action, AnyContent, ControllerComponents, Result}

import javax.inject.{Inject, Singleton}
import scala.language.postfixOps

/**
 * This controller serves expense requests, such as fetch, count, delete, etc.
 */
@Singleton
class StatisticsController @Inject()(cc: ControllerComponents,
                                     auth: SessionAction,
                                     statisticsService: StatisticsService)
  extends JsonController(cc) {

  private val logger: Logger = Logger(this.getClass)

  /**
   * The /api/statistics/year/:year/month/:month GET service pulls expense statistics from
   * dailyexpenses collection, where we compute aggregations every midnight, for faster statistics
   *
   * @return [[play.api.mvc.Result]] (200 OK with expenses statistics info upon success, 404 NotFound if
   *         expenses do
   *         not exist)
   */
  def getDailyStatistics(year: Int, month: Int): Action[AnyContent] = {
    auth { implicit request: SessionRequest[_] =>
      try {
        val maybeInfo = statisticsService.findStatistics(request.userId, year, Some(month))

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retNotFound(Message("No statistics could be found"))
        }
      } catch {
        case t: Throwable => handleServerError(t)
      }
    }
  }

  /**
   * The /api/statistics/year/:year GET service pulls expense statistics from monthlyexpenses collection,
   * where we compute aggregations every month, for faster statistics
   *
   * @return [[play.api.mvc.Result]] (200 OK with expenses statistics info upon success, 404 NotFound if
   *         expenses do
   *         not exist)
   */
  def getMonthlyStatistics(year: Int): Action[AnyContent] = {
    auth { implicit request: SessionRequest[_] =>
      try {
        val maybeInfo = statisticsService.findStatistics(request.userId, year)

        if (maybeInfo.isDefined) {
          retOk(maybeInfo.get)
        } else {
          retNotFound(Message("No statistics could be found"))
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
}

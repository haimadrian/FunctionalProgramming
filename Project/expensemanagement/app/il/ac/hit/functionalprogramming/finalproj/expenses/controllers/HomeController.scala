package il.ac.hit.functionalprogramming.finalproj.expenses.controllers

import play.api.mvc._

import javax.inject._

/**
 * This controller serves home page request ('/').<br/>
 * Just return a Welcome message.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index: Action[AnyContent] = {
    Action {
      Ok("Expense Management App: Welcome to Expense Management service.")
    }
  }
}

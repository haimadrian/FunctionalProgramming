package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.{Inject, Singleton}

/**
 * This controller serves home page request ('/').<br/>
 * Just return a Welcome message.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index: Action[AnyContent] = {
    Action {
      Ok("Expense Management App: Welcome to User Management service.")
    }
  }
}

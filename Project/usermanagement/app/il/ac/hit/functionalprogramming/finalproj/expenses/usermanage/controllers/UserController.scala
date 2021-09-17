package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import javax.inject._
import play.api.mvc._

/**
 * This controller serves user requests, such as sign in, sign out, and info.
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def signup = Action {
    Ok("Your new application is ready.")
  }

}

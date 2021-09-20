package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import play.api.mvc.{Request, WrappedRequest}

/**
 * A request wrapper used to decorate HTTP request with session info, such as user ID and email.<br/>
 * Session info is extracted from JWT in services that use the [[SessionAction]].
 * @author Haim Adrian
 * @since 18 Sep 2021
 */
case class SessionRequest[A](userId: String, email: String, jwt: String, request: Request[A])
  extends WrappedRequest[A](request)

package il.ac.hit.functionalprogramming.finalproj.common.controllers

import play.api.mvc.{Request, WrappedRequest}

/** A request wrapper used to decorate HTTP request with session info, such as user ID and email.<br/>
 * Session info is extracted from JWT in services that use a SessionAction.
 *
 * @author Haim Adrian
 * @since 18 Sep 2021
 */
case class SessionRequest[A](userId: String,
                             email: String,
                             jwt: String = "",
                             request: Request[A] = null) extends WrappedRequest[A](request)

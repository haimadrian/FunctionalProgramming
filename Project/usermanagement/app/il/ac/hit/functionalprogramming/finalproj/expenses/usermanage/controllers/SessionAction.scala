package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.controllers

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.{Message, UserAuthInfo}
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services.{Firebase, UserAuthCache}
import play.api.libs.json.Json
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/**
 * Every action (controller) that requires a [[SessionRequest]] by relying on the
 * Authorization header of a request (JWT) should use this action composition mechanism,
 * to get a reference to our request wrapper which is decorated with user info, and also
 * protected to ignore access of unauthorized requests.<br/>
 * This way we protect server APIs by firebase JWT authorization. Just inject a [[SessionAction]]
 * to your controller's web service implementation, and your service will be secured.
 *
 * @author Haim Adrian
 * @since 18 Sep 2021
 */
@Singleton
class SessionAction @Inject()(playBodyParsers: PlayBodyParsers)
                             (implicit val executionContext: ExecutionContext)
  extends ActionBuilder[SessionRequest, AnyContent] {
  private val AUTHORIZATION_HEADER = "Authorization"

  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](request: Request[A], block: SessionRequest[A] => Future[Result]): Future[Result] = {
    val bearerToken = request.headers.get(AUTHORIZATION_HEADER)

    if (bearerToken.isEmpty || bearerToken.get.trim.isEmpty) {
      // Server cannot recognize the JWT as it is missing. Return 401 UNAUTHORIZED
      Future.successful {
        Unauthorized(Json.toJson(Message(s"$AUTHORIZATION_HEADER header is missing or empty")))
      }
    } else {
      var errorMessage: Option[String] = None
      var sessionRequest: Option[SessionRequest[A]] = None

      try {
        val bearerTokenParts = bearerToken.get.split(' ')
        val jwt = if (bearerTokenParts.length > 1) bearerTokenParts(1) else bearerTokenParts(0)

        val maybeInfo = UserAuthCache.get(jwt)
        if (maybeInfo.isDefined) {
          sessionRequest = Some(SessionRequest[A](maybeInfo.get.userId, maybeInfo.get.userEmail, jwt, request))
        } else {
          val firebaseToken = Firebase.appAuth.verifyIdToken(jwt)

          // Cache authorized user
          UserAuthCache += jwt -> UserAuthInfo(firebaseToken.getUid, firebaseToken.getEmail, jwt)
          sessionRequest = Some(SessionRequest[A](firebaseToken.getUid, firebaseToken.getEmail, jwt, request))
        }
      } catch {
        // Server did recognize the JWT, though it is illegal. Return 403 FORBIDDEN
        case e: Exception => errorMessage = Some(e.getMessage)
      }

      if (sessionRequest.isDefined) {
        // If token is recognized, and hasn't been expired, we get here and pass a session request
        // containing user info to the controller which uses us.
        block(sessionRequest.get)
      } else {
        Future.successful {
          Forbidden(Json.toJson(Message(errorMessage.getOrElse("Forbidden"))))
        }
      }
    }
  }
}

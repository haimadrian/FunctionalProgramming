package il.ac.hit.functionalprogramming.finalproj.expenses.controllers

import il.ac.hit.functionalprogramming.finalproj.expenses.models.{Message, UserAuthInfo}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc._
import play.shaded.ahc.io.netty.handler.codec.http.HttpResponseStatus

import javax.inject.{Inject, Singleton}
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps

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
class SessionAction @Inject()(playBodyParsers: PlayBodyParsers,
                              wsClient: WSClient,
                              config: Configuration)
                             (implicit val executionContext: ExecutionContext)
  extends ActionBuilder[SessionRequest, AnyContent] {
  private val AUTHORIZATION_HEADER = "Authorization"

  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](request: Request[A],
                              block: SessionRequest[A] => Future[Result]): Future[Result] = {
    val bearerToken = request.headers.get(AUTHORIZATION_HEADER)

    if (bearerToken.isEmpty || bearerToken.get.trim.isEmpty) {
      // Server cannot recognize the JWT as it is missing. Return 401 UNAUTHORIZED
      Future.successful {
        Unauthorized(Json.toJson(Message(s"$AUTHORIZATION_HEADER header is missing or empty")))
      }
    } else {
      val isAuthRequest: WSRequest =
        wsClient
          .url(s"${config.get[String]("backend.host.userManagement")}/api/auth")
          .addHttpHeaders("Accept" -> "application/json")
          .addHttpHeaders("Authorization" -> bearerToken.get)
          .withRequestTimeout(5 seconds)

      val response = Await.result(isAuthRequest.get(), 5 seconds)
      if (response.status == HttpResponseStatus.OK.code()) {
        // If token is recognized, and hasn't been expired, we get here and pass a session request
        // containing user info to the controller which uses us.
        val jsonRes = response.json
        val userAuthInfo = jsonRes.validate[UserAuthInfo]
        block(SessionRequest(userAuthInfo.get.userId, userAuthInfo.get.userEmail, request))
      } else {
        Future.successful {
          Forbidden(Json.toJson(Message("Forbidden")))
        }
      }
    }
  }
}

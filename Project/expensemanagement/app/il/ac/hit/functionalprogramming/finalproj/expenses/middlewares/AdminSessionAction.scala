package il.ac.hit.functionalprogramming.finalproj.expenses.middlewares

import il.ac.hit.functionalprogramming.finalproj.common.controllers.SessionRequest
import il.ac.hit.functionalprogramming.finalproj.common.models.Message
import il.ac.hit.functionalprogramming.finalproj.common.utils.PasswordEncoder
import org.apache.http.HttpHeaders
import pdi.jwt.{JwtAlgorithm, JwtJson}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

/**
 * This class created to protect services by Admin JWT verification.<br/>
 * We use our MongoDB credentials (encoded) as JWT payload in order to authorize micro-services
 * with one another. Thus for example we can protect the
 * [[il.ac.hit.functionalprogramming.finalproj.expenses.controllers.ExpenseController.fetchAllExpenses]] to
 * make sure only admin can use that API, as it exposes data of all of the users, and not a single user.
 *
 * @author Haim Adrian
 * @since 24 Sep 2021
 */
@Singleton
class AdminSessionAction @Inject()(playBodyParsers: PlayBodyParsers, config: Configuration)
                                  (implicit val executionContext: ExecutionContext)
  extends ActionBuilder[SessionRequest, AnyContent] {

  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](request: Request[A],
                              block: SessionRequest[A] => Future[Result]): Future[Result] = {
    val bearerToken = request.headers.get(HttpHeaders.AUTHORIZATION)

    if (bearerToken.isEmpty || bearerToken.get.trim.isEmpty) {
      // Server cannot recognize the JWT as it is missing. Return 401 UNAUTHORIZED
      Future.successful {
        Unauthorized(Json.toJson(Message(s"${HttpHeaders.AUTHORIZATION} header is missing or empty")))
      }
    } else {
      var errorMessage: Option[String] = None
      var sessionRequest: Option[SessionRequest[A]] = None

      try {
        val bearerTokenParts = bearerToken.get.split(' ')
        val jwt = if (bearerTokenParts.length > 1) {
          bearerTokenParts(1)
        } else {
          bearerTokenParts(0)
        }

        val key = config.get[String]("jwt.key")
        val claim = JwtJson.decode(jwt, key, Seq(JwtAlgorithm.HS256))

        if (claim.isSuccess && PasswordEncoder.encrypt(key).equals(claim.get.content)) {
          sessionRequest = Some(SessionRequest[A]("admin", "", jwt, request))
        } else {
          errorMessage = Some("Wrong JWT")
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

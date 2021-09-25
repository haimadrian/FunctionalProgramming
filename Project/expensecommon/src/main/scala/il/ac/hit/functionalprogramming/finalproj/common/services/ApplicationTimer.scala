package il.ac.hit.functionalprogramming.finalproj.common.services

import play.api.Logger
import play.api.inject.ApplicationLifecycle

import java.time.{Clock, Instant}
import javax.inject._
import scala.concurrent.Future

/** This class starts a timer when the application starts. When the application stops it prints out how
 * long the application was running for.
 * This class needs to run code when the server stops. It uses the application's [[ApplicationLifecycle]] to register
 * a stop hook.
 */
@Singleton
class ApplicationTimer @Inject()(clock: Clock, appLifecycle: ApplicationLifecycle) {

  private val logger: Logger = Logger(this.getClass)

  // This code is called when the application starts.
  private val start: Instant = clock.instant
  logger.info(s"ApplicationTimer: Starting application at $start.")

  // When the application starts, register a stop hook with the
  // ApplicationLifecycle object. The code inside the stop hook will
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    val runningTime: Long = clock.instant.getEpochSecond - start.getEpochSecond
    logger.info(s"ApplicationTimer: Stopping application at ${clock.instant} after ${runningTime}s.")
    Future.successful(())
  }
}

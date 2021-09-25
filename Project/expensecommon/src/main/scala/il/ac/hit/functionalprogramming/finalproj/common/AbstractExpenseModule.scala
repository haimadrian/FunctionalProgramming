package il.ac.hit.functionalprogramming.finalproj.common

import com.google.inject.AbstractModule
import il.ac.hit.functionalprogramming.finalproj.common.services.ApplicationTimer
import play.api.Logger

import java.time.Clock

/**
 * This class is a Guice module that tells Guice how to bind several different types.
 * This Guice module is created when the Play application starts.
 *
 * This class created as a base class for our Play modules to extend, without having to repeat
 * common bindings.
 */
abstract class AbstractExpenseModule extends AbstractModule {
  val logger: Logger = Logger(getClass)

  def configureExpenseModule(): Unit

  override final def configure(): Unit = {
    logger.info(s"${getClass.getSimpleName} configure")

    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    // Ask Guice to create an instance of ApplicationTimer when the application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()

    configureExpenseModule()

    logger.info(s"${getClass.getSimpleName} ready")
  }

}

package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services.{ApplicationTimer, CustomObjectMapper, MongoService, UserMongoDBService, UserService}
import play.api.Logger

import java.time.Clock

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class UserManagementModule extends AbstractModule {
  val logger: Logger = Logger(getClass)

  override def configure(): Unit = {
    logger.info("User Management configure")

    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)

    // Ask Guice to create an instance of ApplicationTimer when the application starts.
    bind(classOf[ApplicationTimer]).asEagerSingleton()

    // Use our configured Jackson
    bind(classOf[ObjectMapper]).toProvider(CustomObjectMapper).asEagerSingleton()

    // Init MongoDB connection so we can work with MongoDB
    bind(classOf[MongoService]).asEagerSingleton()

    // Init UserService so we can CRUD users
    bind(classOf[UserService]).to(classOf[UserMongoDBService])

    logger.info("User Management ready")
  }

}

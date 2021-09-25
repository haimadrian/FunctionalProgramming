package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage

import il.ac.hit.functionalprogramming.finalproj.common.AbstractExpenseModule
import il.ac.hit.functionalprogramming.finalproj.common.services.MongoService
import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services._

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
class UserManagementModule extends AbstractExpenseModule {

  override def configureExpenseModule(): Unit = {
    // Init MongoDB connection so we can work with MongoDB
    bind(classOf[MongoService]).asEagerSingleton()

    // Init UserService so we can CRUD users
    bind(classOf[UserService]).to(classOf[UserMongoDBService])
  }

}

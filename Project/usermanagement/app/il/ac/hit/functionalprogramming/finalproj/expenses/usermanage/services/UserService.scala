package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserInfo

/**
 * A trait to expose CRUD functionality for users table.<br/>
 * We inject a mongo implementation of this trait.
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
trait UserService {
  /**
   * Inserts a new user document to mongo DB users collection
   *
   * @param userId    The userId (firebase) to use, received from client
   * @param userEmail The email of the registered user
   * @return Optional user info. (UserInfo upon success, and None upon failure)
   */
  def insertUser(userId: String, userEmail: String): Option[UserInfo]

  /**
   * Read a user document from mongo DB users collection
   *
   * @param userId The userId (firebase) to find, received from client
   * @return Optional user info. (UserInfo upon success, and None upon failure or not exists)
   */
  def findUser(userId: String): Option[UserInfo]

  /**
   * Update a user document in mongo DB users collection
   *
   * @param userId   The userId (firebase) to update, received from client
   * @param userInfo User info to update. (Email is ignored)
   * @return Optional user info. (UserInfo upon success, and None upon failure)
   */
  def updateUser(userId: String, userInfo: UserInfo): Option[UserInfo]
}

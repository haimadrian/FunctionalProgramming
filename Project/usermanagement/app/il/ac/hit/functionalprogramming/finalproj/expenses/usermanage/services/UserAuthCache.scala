package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services

import il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.models.UserAuthInfo

import scala.collection.mutable

/**
 * A singleton responsible for keeping signed in users, so we can avoid of flooding firebase API
 * for user authorization. Once a user is authorized we cache it, so next runs we will skip Firebase.
 *
 * @author Haim Adrian
 * @since 20 Sep 2021
 */
object UserAuthCache extends mutable.Map[String, UserAuthInfo] {
  private val cache: mutable.Map[String, UserAuthInfo] = mutable.Map[String, UserAuthInfo]()

  /**
   * Support indexer such that we can access by key
   *
   * @param jwt A JWT string to get its mapped (already authorized) [[UserAuthInfo]]
   * @return User info
   */
  override def apply(jwt: String): UserAuthInfo = cache(jwt)

  /**
   * @see [[mutable.Map.addOne]]
   */
  override def addOne(elem: (String, UserAuthInfo)): UserAuthCache.this.type = {
    cache.addOne(elem)
    this
  }

  /**
   * @see [[mutable.Map.get]]
   */
  override def get(key: String): Option[UserAuthInfo] = cache.get(key)

  /**
   * @see [[mutable.Map.iterator]]
   */
  override def iterator: Iterator[(String, UserAuthInfo)] = cache.iterator

  /**
   * @see [[mutable.Map.subtractOne]]
   */
  override def subtractOne(elem: String): UserAuthCache.this.type = {
    cache.subtractOne(elem)
    this
  }
}

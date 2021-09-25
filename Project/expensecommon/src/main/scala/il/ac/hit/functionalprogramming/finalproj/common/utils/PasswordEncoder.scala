package il.ac.hit.functionalprogramming.finalproj.common.utils

import java.security.MessageDigest

/**
 * Used to encode password when micro-services communicate with one another on their own,
 * and not when there is a client request with the user's JWT.
 * @author Haim Adrian
 * @since 25 Sep 2021
 */
object PasswordEncoder {
  /**
   * Password Hashing Using Message Digest Algo
   */
  def encrypt(password: String): String = {
    val algorithm: MessageDigest = MessageDigest.getInstance("SHA-256")
    val defaultBytes: Array[Byte] = password.getBytes
    algorithm.reset()
    algorithm.update(defaultBytes)
    val messageDigest: Array[Byte] = algorithm.digest

    val hexString: StringBuilder = new StringBuilder
    messageDigest foreach { digest =>
      val hex = Integer.toHexString(0xFF & digest)
      if (hex.length == 1) {
        hexString.append('0')
      } else {
        hexString.append(hex)
      }
    }

    hexString.toString
  }

}

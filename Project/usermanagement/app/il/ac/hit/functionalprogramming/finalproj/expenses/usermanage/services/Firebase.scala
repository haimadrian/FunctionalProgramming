package il.ac.hit.functionalprogramming.finalproj.expenses.usermanage.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.common.base.Charsets
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.{FirebaseApp, FirebaseOptions}
import org.apache.commons.io.IOUtil
import play.api.Logger

import java.io.{ByteArrayInputStream, IOException}

/**
 * @author Haim Adrian
 * @since 18 Sep 2021
 */
object Firebase {
  private val logger: Logger = Logger(this.getClass)

  /**
   * Name of the project as defined at firebase
   */
  private val FIREBASE_APP_NAME = "expenseapphit"

  /**
   * Name of the firebase project key file, so we can configure firebase admin sdk.
   */
  private val FIREBASE_ADMIN_SDK_CONF = "expenseapphit-firebase-adminsdk-1qhts-f00f7381b7.json"

  /**
   * Reference to [[FirebaseAuth]] for performing JWT authorization.
   */
  val appAuth: FirebaseAuth = initApp()

  /**
   * Configure firebase admin SDK and prepare it for usage by Play server
   *
   * @throws IOException In case config file could not be found
   */
  private def initApp(): FirebaseAuth = {
    logger.info("Initializing Firebase authorization")

    var firebaseApp: Option[FirebaseApp] = None

    try {
      // The annoying Play framework forcibly reloads singletons... So make sure we have not
      // initialized FirebaseApp already...
      firebaseApp = Some(FirebaseApp.getInstance(FIREBASE_APP_NAME))
    } catch {
      case _: IllegalStateException => firebaseApp = None
    }

    if (firebaseApp.isEmpty) {
      val configFile = getClass.getClassLoader.getResourceAsStream(FIREBASE_ADMIN_SDK_CONF)
      val json = IOUtil.toString(configFile, Charsets.UTF_8.name)
      val serviceAccount = new ByteArrayInputStream(json.getBytes(Charsets.UTF_8))
      val options = FirebaseOptions.builder.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build
      firebaseApp = Some(FirebaseApp.initializeApp(options, FIREBASE_APP_NAME))
      logger.info("Firebase authorization initialized")
    }

    FirebaseAuth.getInstance(firebaseApp.get)
  }
}

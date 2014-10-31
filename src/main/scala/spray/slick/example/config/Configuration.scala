package spray.slick.example.config

import com.typesafe.config.ConfigFactory

import scala.util.Try

/**
 * Created by pmincz on 12/07/14.
 */
trait Configuration {
  /**
   * Application config object.
   * Read the configuration from the application.conf file
   */
  val config = ConfigFactory.load()

  /** Host name/address to start service on. */
  lazy val serviceHost = Try(config.getString("service.host")).getOrElse("localhost")

  /** Port to start service on. */
  lazy val servicePort = Try(config.getInt("service.port")).getOrElse(8080)
}

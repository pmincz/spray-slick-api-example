package spray.slick.example.config

import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by pmincz on 10/11/14.
 */
object DbProvider {

  val db = Database.forURL("jdbc:h2:mem:test1;MODE=MYSQL;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

}
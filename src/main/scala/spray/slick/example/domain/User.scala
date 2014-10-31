package spray.slick.example.domain

import spray.slick.example.domain.util.{BaseTable, BaseEntity}
import scala.slick.lifted.Tag
import scala.slick.driver.MySQLDriver.simple._
import scala.language.implicitConversions

/**
 * Created by pmincz on 31/10/14.
 */
case class User(id: Option[Long], name: String, password: String, email: String) extends BaseEntity

/**
 * Mapped users table object.
 */
class Users(tag: Tag) extends BaseTable[User](tag, "usuarios") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc, O.Nullable)

  def name = column[String]("name")

  def password = column[String]("password")

  def email = column[String]("email")

  def * = (id.?, name, password, email) <> (User.tupled, User.unapply _)

}
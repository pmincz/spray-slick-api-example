package spray.slick.example.dao

import java.sql.SQLException

import spray.slick.example.dao.util.BaseDAO
import spray.slick.example.domain.util.Error
import spray.slick.example.domain.{User, Users}
import scala.slick.lifted.TableQuery
import scala.language.implicitConversions
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by pmincz on 31/10/14.
 */
class UserDAO extends BaseDAO[User, Users]("id") {

  val tableQuery = TableQuery[Users]
  create

  private val usersAutoInc = tableQuery.map(u => (u.name, u.password, u.email)) returning tableQuery.map(_.id) into {
    case (_, id) => id
  }

  override def insert(user: User): Either[Error, Long] = {
    try {
      db withSession { implicit session =>
        val id = usersAutoInc.insert(user.name, user.password, user.email)
        Right(id.toLong)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

}

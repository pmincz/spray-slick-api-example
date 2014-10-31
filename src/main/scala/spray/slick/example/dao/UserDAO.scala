package spray.slick.example.dao

import spray.slick.example.dao.util.BaseDAO
import spray.slick.example.domain.{User, Users}

import scala.slick.lifted.TableQuery

/**
 * Created by pmincz on 31/10/14.
 */
class UserDAO extends BaseDAO[User, Users]("id") {

  val tableQuery = TableQuery[Users]

}

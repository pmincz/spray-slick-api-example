package spray.slick.example.service

import spray.slick.example.dao.UserDAO
import spray.slick.example.domain.{User, Users}
import spray.slick.example.service.util.BaseService

/**
 * Created by pmincz on 01/11/14.
 */
object UserService extends BaseService[User, Users, UserDAO] {

  val dao = new UserDAO

}

package spray.slick.example.rest

import spray.http.MediaTypes
import spray.slick.example.rest.util.BaseRestService
import spray.slick.example.service.UserService

import scala.concurrent.{Future, ExecutionContext}


/**
 * Created by pmincz on 01/11/14.
 */
trait UserRestService extends BaseRestService {

  val userService = UserService
  def userRoute(implicit exc: ExecutionContext) = getUserById ~ getAllUsers

  def getUserById(implicit exc: ExecutionContext) = respondWithMediaType(MediaTypes.`application/json`) {
    get {
      path("users" / LongNumber) { thisId =>
        complete {
          Future {
            userService.get(thisId)
          }
        }
      }
    }
  }

  def getAllUsers(implicit exc: ExecutionContext) = respondWithMediaType(MediaTypes.`application/json`) {
    get {
      path("users") {
        pagingParams { sort =>
          urlParams { params =>
            complete {
              Future {
                userService.getAll(params, sort)
              }
            }
          }
        }
      }
    }
  }

}

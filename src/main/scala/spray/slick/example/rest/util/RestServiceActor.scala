package spray.slick.example.rest.util

import akka.actor.Actor
import spray.slick.example.rest.UserRestService

/**
 * Created by pmincz on 31/10/14.
 *
 * Actor that use all the routes and generate the service
 * If you want to add a new ---RestService you have
 * to add the trait with the "with" word and then modify the runRoute(usersRoute)
 * with runRoute(usersRoute ~ "the name of the route")
 */
class RestServiceActor extends Actor with UserRestService {

  def actorRefFactory = context

  def receive = runRoute(userRoute)

}

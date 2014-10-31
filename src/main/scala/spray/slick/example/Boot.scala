package spray.slick.example

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import spray.slick.example.config.Configuration

/**
 * Created by pmincz on 31/10/14.
 */
object Boot extends App with Configuration {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("spray-api-example")

  // create and start our service actor
  val service = system.actorOf(Props[RestServiceActor], "spray-api-example-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = serviceHost, port = servicePort)

}

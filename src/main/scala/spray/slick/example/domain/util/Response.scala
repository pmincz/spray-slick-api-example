package spray.slick.example.domain.util

import scala.reflect.runtime.{currentMirror => m, universe => ru}

/**
 * Created by pmincz on 1/29/14.
 */
case class Response[A](items: A, paging: Paging)

case class Paging(offset: Int, limit: Int, total: Int)
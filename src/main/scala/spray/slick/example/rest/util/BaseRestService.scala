package spray.slick.example.rest.util

import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.routing._
import spray.slick.example.domain.util.{UtilsError, SortPageInfo}

/**
 * Created by pmincz on 31/10/14.
 *
 * All the traits that have the routes extends from this trait
 * It has the executionContext & the DefaultFormats for the json marshalling
 */
trait BaseRestService extends HttpService with Json4sSupport with Directives with UtilsError {

  implicit def executionContext = actorRefFactory.dispatcher

  val json4sFormats = DefaultFormats

  val urlParams = parameterSeq

  val pagingParams = parameters('offset.as[Int] ? 0, 'limit.as[Int] ? 15, 'order_by ? "").as(SortPageInfo)

  def validateParams(params: Seq[(String, String)], mandatoryParams: List[String]) : Directive0 = if (!validateMandatoryParams(params, mandatoryParams)) reject(paramsError("mandatory params are necessary: " + mandatoryParams.toString)) else pass

  def validateMandatoryParams(params: Seq[(String, String)], mandatoryParams: List[String]): Boolean = {
    def paramsMap = params.map(a => a._1 -> a._2).toMap
    if (mandatoryParams.isEmpty) true
    else mandatoryParams.map(paramsMap.contains(_)).find(_ == false).getOrElse(true)
  }

}

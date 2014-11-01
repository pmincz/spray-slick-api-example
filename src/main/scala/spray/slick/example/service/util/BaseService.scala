package spray.slick.example.service.util

import spray.slick.example.dao.util.BaseDAO
import spray.slick.example.domain.util._

/**
 * Created by pmincz on 01/11/14.
 */
abstract class BaseService[T <: BaseEntity, DB <: BaseTable[T], D <: BaseDAO[T, DB]] {

  val dao: D

  def getAll(params: Seq[(String, String)], sort: SortPageInfo): Response[List[T]] = {
    baseListFunction(params, sort, dao.getAll)
  }

  def baseListFunction(params: Seq[(String, String)], sort: SortPageInfo, function : (Map[String, String], SortPageInfo) => (Either[Error, List[T]]) ): Response[List[T]] = {
    val paramsMap = getParams(params)
    function(paramsMap, sort) match {
      case Left(x: Error) => throw x
      case Right(x) => convertToResponseEntity(x, sort, dao.getTotal(paramsMap))
    }
  }

  def create(entity: T): Long = {
    dao.insert(entity) match {
      case Left(e: Error) => throw e
      case Right(r) => r
    }
  }

  def get(id: Long): T = {
    dao.get(id) match {
      case Left(e: Error) => throw e
      case Right(r) => r
    }
  }

  def update(entity: T) = {
    dao.update(entity)
  }

  def delete(entity: T): Boolean = {
    dao.delete(entity.id.get) match {
      case Left(e: Error) => throw e
      case Right(r: Int) => r > 0
    }
  }

  def delete(id: Long): Boolean = {
    dao.delete(id) match {
      case Left(e: Error) => throw e
      case Right(r: Int) => r > 0
    }
  }

  def convertToResponseEntity(items: List[T], sort: SortPageInfo, total: Int): Response[List[T]] = {
    Response(items, Paging(sort.offset, sort.limit, total))
  }

  def getParams(params: Seq[(String, String)]): Map[String, String] = {
    params.map(a => a._1 -> a._2).toMap
  }

}

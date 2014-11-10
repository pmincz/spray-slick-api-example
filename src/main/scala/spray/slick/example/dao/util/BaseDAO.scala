package spray.slick.example.dao.util

import java.sql._
import spray.slick.example.config.DbProvider
import spray.slick.example.domain.util.{SortPageInfo, UtilsError, BaseTable, BaseEntity}
import scala.language.implicitConversions
import scala.reflect.ClassTag
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.lifted.TableQuery
import spray.slick.example.domain.util._

/**
 * Created by pmincz on 12/26/13.
 */
abstract class BaseDAO[T <: BaseEntity : ClassTag, DB <: BaseTable[T]](idName: String) extends UtilsError {

  val db = DbProvider.db

  val tableQuery: TableQuery[DB]

  def get(id: Long): Either[Error, T] = {
    try {
      db withSession { implicit session =>
        val query = for {records <- tableQuery.filter(_.column[Long](idName) === id)} yield (records)
        query.firstOption match {
          case Some(record: T) => Right(record)
          case _ =>
            Left(notFoundError(id))
        }
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def getAll(params: Map[String, String], sort: SortPageInfo): Either[Error, List[T]] = {
    try {
      db withSession { implicit session =>
        val query = for {records <- tableQuery} yield (records)

        val inter = query.drop(sort.offset).take(sort.limit).mapResult {
          case record: T => record
        }

        val recordsWithPagination = inter.list
        Right(recordsWithPagination)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def getTotal(params: Map[String, String]): Int = {
    db withSession { implicit session =>
      val query = for {f <- tableQuery} yield (f.column[Long](idName))
      query.list.size
    }
  }

  def getAll: Either[Error, List[T]] = {
    try {
      db withSession { implicit session =>
        val query = for {records <- tableQuery} yield (records)
        val inter = query.mapResult {
          case record: T => record
        }

        val list = inter.list
        Right(list)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def insert(entity: T): Either[Error, Long] = {
    try {
      db withSession { implicit session =>
        Right((tableQuery returning tableQuery.map(_.column[Long](idName))) += entity)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def update(entity: T) = {
    try {
      db withSession { implicit session =>
        val query = for {records <- tableQuery.filter(_.column[Long](idName) === entity.id)} yield (records)
        query.update(entity)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def delete(id: Long): Either[Error, Int] = {
    try {
      db withSession { implicit session =>
        val query = for {records <- tableQuery.filter(_.column[Long](idName) === id)} yield (records)
        Right(query.delete)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

  def create: Either[Error, Unit] = {
    try {
      db withSession { implicit session =>
        Right(tableQuery.ddl.create)
      }
    } catch {
      case e: SQLException =>
        Left(databaseError(e))
    }
  }

}
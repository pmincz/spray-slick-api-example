package spray.slick.example.domain.util

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by pmincz on 31/10/14.
 */
abstract class BaseTable[T <: BaseEntity](tag: Tag, tableName: String) extends Table[T](tag, tableName)

trait BaseEntity {
  def id: Option[Long]
}

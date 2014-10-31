package spray.slick.example.domain.util

import java.sql._

import spray.http.StatusCodes
import spray.routing.Rejection

/**
 * Service error description.
 *
 * @param message   error message
 * @param code error type
 */
case class Error(message: String, code: Int, statusCode: Int = 0) extends Throwable with Rejection {
  def this(message: String) = this(message, 400)
}

/**
 * Response error for spray
 *
 * @param message
 * @param code
 */
case class ErrorResponse(message: String, code: Int)

trait UtilsError {
  /**
   * Produce database error description.
   *
   */
  def databaseError(e: SQLException) =
    Error("%d: %s".format(e.getErrorCode, e.getMessage), StatusCodes.InternalServerError.intValue)

  /**
   * Produce database error description.
   *
   */
  def paramsError(message: String) =
    Error("%s".format(message), StatusCodes.BadRequest.intValue)

  /**
   * Produce entity not found error description.
   *
   */
  def notFoundError(id: Long) =
    Error("Entity with id=%d does not exist".format(id), StatusCodes.NotFound.intValue)

  /**
   * Produce entity not found error description.
   *
   */
  def notFoundError(search: String) =
    Error("Entity with field %s does not exist".format(search), StatusCodes.NotFound.intValue)

  /**
   * Produce entity not found error description.
   *
   */
  def notFoundError(username: String, password: String) =
    Error("Entity with username=%s and password=%s does not exist".format(username, password), StatusCodes.NotFound.intValue)

  /**
   * Produce entity not found error description.
   *
   */
  def notFoundError =
    Error("Entity does not exist", StatusCodes.NotFound.intValue)

}
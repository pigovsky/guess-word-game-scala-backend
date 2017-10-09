package com.pigovsky.guess_word_game.backend.extensions

import akka.http.scaladsl.model._
import cats.data.Xor
import com.pigovsky.guess_word_game.backend.extensions.AkkaHttpResponses.AkkaHttpResponse
import com.pigovsky.guess_word_game.backend.extensions.AllAkkaHttpExtensions.HttpResponseOrExtension
import types.error.AppErrorResult
import scalty.types.{AllTypesAlias, AllTypesExtensions, Or}
import spray.json.JsValue

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions

/**
  * Created by stefaniv on 23.05.17.
  */
trait AllAkkaHttpExtensions {
  implicit def httpResponseOrExtension[T](or: Or[T]): HttpResponseOrExtension[T] = new HttpResponseOrExtension[T](or)
}

object AllAkkaHttpExtensions extends AllTypesExtensions with AllTypesAlias {

  implicit final class HttpResponseOrExtension[T](val or: Or[T]) extends AnyVal {
    def toAsyncResponse(implicit executionContext: ExecutionContext): Future[AkkaHttpResponse] = or.value.map {
      case Xor.Right(_)                       => AkkaHttpResponses.ok
      case Xor.Left(appError: AppErrorResult) => AkkaHttpResponses.badRequest(appError.toJson)
      case Xor.Left(appError: AppError)       => AkkaHttpResponses.badRequest(appError.toString)
    }

    def toAsyncResponse(transformer: T => JsValue)(implicit executionContext: ExecutionContext): Future[AkkaHttpResponse] =
      or.value.map {
        case Xor.Right(value)                   => AkkaHttpResponses.ok(transformer(value))
        case Xor.Left(appError: AppErrorResult) => AkkaHttpResponses.badRequest(appError.toJson)
        case Xor.Left(appError: AppError)       => AkkaHttpResponses.badRequest(appError.toString)
      }
  }

}

object AkkaHttpResponses {

  /** Status code and JSON as string */
  type AkkaHttpResponse = HttpResponse

  val ok: AkkaHttpResponse =
    HttpResponse(status = StatusCodes.OK, entity = StatusCodes.OK.defaultMessage)

  def ok(json: JsValue): AkkaHttpResponse =
    HttpResponse(status = StatusCodes.OK, entity = HttpEntity(ContentTypes.`application/json`, json.compactPrint))

  def badRequest(message: String): AkkaHttpResponse =
    HttpResponse(status = StatusCodes.BadRequest, entity = message)

  def badRequest(json: JsValue): AkkaHttpResponse =
    HttpResponse(status = StatusCodes.BadRequest, entity = HttpEntity(ContentTypes.`application/json`, json.compactPrint))

}

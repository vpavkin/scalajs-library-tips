package utils

import cats.data.Xor

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.{JavaScriptException, Promise}

object promise {
  implicit class JSFutureOps[R: ClassTag](f: Future[Xor[String, R]]) {

    def toPromise(implicit ectx: ExecutionContext): Promise[R] =
      f.flatMap[R] {
        case Xor.Right(res) => Future.successful(res)
        case Xor.Left(str) => Future.failed(new JavaScriptException(str))
      }.toJSPromise
  }
}

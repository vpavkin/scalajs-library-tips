package utils

import lib.promise.Promise

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import scala.scalajs.js
import scalaz._
import Scalaz._
import scala.scalajs.js.JSConverters._

object promise {
  implicit class JSFutureOps[R: ClassTag, E: ClassTag](f: Future[\/[E, R]]) {
    def toPromise(recovery: Throwable => js.Any)(implicit ectx: ExecutionContext): Promise[R] =
      new Promise[R]((resolve: js.Function1[R, Unit], reject: js.Function1[js.Any, Unit]) => {
        f.onSuccess({
          case \/-(f: R) => resolve(f)
          case -\/(e: E) => reject(e.asInstanceOf[js.Any])
        })
        f.onFailure {
          case e: Throwable => reject(recovery(e))
        }
      })
  }
}

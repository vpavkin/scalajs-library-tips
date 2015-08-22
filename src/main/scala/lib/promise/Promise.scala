package lib.promise {

import scala.scalajs.js
import js.annotation._

trait Thenable[R] extends js.Object {
  @JSName("then")
  def andThen[U](onFulfilled: js.Function1[R, U] = ???, onRejected: js.Function1[js.Any, U] = ???): Thenable[U] = js.native
}

@JSName("Promise")
class Promise[R] extends Thenable[R] {
  def this(callback: js.Function2[js.Function1[R, Unit], js.Function1[js.Any, Unit], Unit]) = this()
  @JSName("then")
  override def andThen[U](onFulfilled: js.Function1[R, U] = ???, onRejected: js.Function1[js.Any, U] = ???): Promise[U] = js.native
  @JSName("catch")
  def catchError[U](onRejected: js.Function1[js.Any, U] = ???): Promise[U] = js.native
}

@JSName("Promise")
object Promise extends js.Object {
  @JSName("resolve")
  def resolveFlat[R](value: Thenable[R] = ???): Promise[R] = js.native
  def resolve[R](value: R = ???): Promise[R] = js.native
  def reject[R](error: js.Any): Promise[R] = js.native
  def all[R](promises: js.Array[R]): Promise[js.Array[R]] = js.native
  def race[R](promises: js.Array[R]): Promise[R] = js.native
}
}

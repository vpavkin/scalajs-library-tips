package lib

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSName("Promise")
class Promise[+R](callback: js.Function2[js.Function1[R, Unit], js.Function1[js.Any, Unit], Unit])
  extends org.scalajs.dom.raw.Promise[R]

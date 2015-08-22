package lib

import scala.scalajs.js
import scala.scalajs.js.annotation._

@JSName("Promise")
class Promise[+R](executor: js.Function2[js.Function1[R, Any], js.Function1[Any, Any], Any])
  extends org.scalajs.dom.raw.Promise[R]

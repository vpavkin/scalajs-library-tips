package utils

import scala.reflect._
import scala.scalajs.js.annotation.JSExport

trait Typed { self =>
  @JSExport("type")
  def typ: String = self.getClass.getSimpleName
}

class TypeNameConstant[T: ClassTag] {
  @JSExport("type")
  def typ: String = classTag[T].runtimeClass.getSimpleName
}

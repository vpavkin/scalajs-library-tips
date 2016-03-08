package utils

import scala.reflect._
import scala.scalajs.js.annotation.JSExport

trait Typed { self =>
  @JSExport("type")
  def `type`: String = self.getClass.getSimpleName
}

class TypeNameConstant[T: ClassTag] {
  @JSExport("type")
  def `type`: String = classTag[T].runtimeClass.getSimpleName
}

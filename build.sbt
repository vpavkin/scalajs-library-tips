enablePlugins(ScalaJSPlugin)

name := "scala-js-library-tips"

libraryDependencies ++= Seq(
  "org.typelevel" %%% "cats-core" % "0.4.1",
  "org.scala-js" %%% "scalajs-dom" % "0.9.0"
)

scalaVersion := "2.11.7"

lazy val buildJS = taskKey[Unit]("Prepare a production js build")
buildJS.in(Compile) := {
  "rm -rf ./build" !;
  "mkdir ./build -p" !;
  s"cp ./target/scala-2.11/${name.value}-opt.js ./build/${name.value}.js" !;
  s"cp ./target/scala-2.11/${name.value}-opt.js.map ./build/${name.value}.js.map" !
}
buildJS.in(Compile) <<= buildJS.in(Compile).dependsOn(clean, fullOptJS.in(Compile))

lazy val releaseJS = taskKey[Unit]("Prepare a release archive")
releaseJS.in(Compile) := {
  s"rm -f release-${version.value}.zip" !;
  s"zip -j release-${version.value}.zip ./build/${name.value}.js" !;
  s"zip -j release-${version.value}.zip ./build/${name.value}.js.map" !
}

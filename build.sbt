name := "ApacheAtlasSimplified"

version := "0.1"

scalaVersion := "2.11.12"

val catsV = "1.6.0"
val atlasV = "1.1.0"

lazy val projectDeps = Seq(
  "org.typelevel" %% "cats-core" % catsV withSources(),
  "org.typelevel" %% "cats-kernel" % catsV withSources(),
  "org.typelevel" %% "cats-macros" % catsV withSources(),
  "com.github.pureconfig" %% "pureconfig" % "0.9.2",
  "com.github.mpilquist" %% "simulacrum" % "0.16.0",
  "org.apache.atlas" % "atlas-notification" % atlasV,
  "org.apache.atlas" % "atlas-intg" % atlasV,
  "org.apache.atlas" % "atlas-client-v2" % atlasV,
  "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= projectDeps

scalacOptions ++= Seq("-Ypartial-unification", "-unchecked", "-deprecation", "-feature", "-target:jvm-1.8", "-Ywarn-unused-import")
scalacOptions in(Compile, doc) ++= Seq("-unchecked", "-deprecation", "-diagrams", "-implicits")

fork in Test := true
javaOptions ++= Seq("-Xms2g", "-Xmx3g", "-XX:+CMSClassUnloadingEnabled")
parallelExecution in Test := true


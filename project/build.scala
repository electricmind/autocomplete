import sbt.Keys._
import sbt._

object AutocompleteBuild extends Build {
  val utils =
    RootProject(uri("https://github.com/electricmind/utils.git#v.2.1"))

  val enwiz =
    RootProject(uri("https://github.com/electricmind/enwiz.git#scala-2_11"))

  val Organization = "io.github.electricmind"
  val Name = "Autocomplete"
  val ScalaVersion = "2.11.7"

  lazy val project = Project(
    Name,
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      organization := Organization,
      name := Name
    )
  ).dependsOn(utils).dependsOn(enwiz)
}



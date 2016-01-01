import sbt._
import Keys._

object AutocompleteBuild extends Build {
  val Organization = "io.github.electricmind"
  val Name = "Autocomplete"
  val Version = "0.10.0"
  val ScalaVersion = "2.11.7"
 
  lazy val project = Project (
    "Autocomplete",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
       organization := Organization,
       name := Name
 )

).dependsOn ( utils ).dependsOn ( enwiz )

lazy val utils =
        RootProject(uri("https://github.com/electricmind/utils.git#scala-2_11"))

lazy val enwiz =
        RootProject(uri("https://github.com/electricmind/enwiz.git#scala-2_11"))
}



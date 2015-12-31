import sbt._
import Keys._

object NovelBuild extends Build {
  val Organization = "io.github.electricmind"
  val Name = "Autocomplete"
  val Version = "0.10.0"
  val ScalaVersion = "2.10.5"
 
  lazy val project = Project (
    "Autocomplete",
    file("."),
    settings = Defaults.defaultSettings ++ /*RevolverPlugin.settings ++ Twirl.settings ++*/ Seq(
       organization := Organization,
       name := Name//,
//       scalaVersion := ScalaVersion//,
//       libraryDependencies ++= {
//       }
 )

).dependsOn ( utils ).dependsOn ( enwiz )

lazy val utils =
        RootProject(uri("https://github.com/electricmind/utils.git#smartfile_append"))

lazy val enwiz =
        RootProject(uri("https://github.com/electricmind/enwiz.git"))
}



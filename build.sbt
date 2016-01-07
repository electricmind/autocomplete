name := "autocomplete"

organization := "io.github.electricmind"

version := "1.1"

scalaVersion := "2.11.7"

mainClass := Some("io.github.dronegator.electricmind.Autocomplete")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")


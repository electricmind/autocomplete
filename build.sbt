name := "autocomplete"

organization := "io.github.electricmind"

version := "1.2"

scalaVersion := "2.11.7"

mainClass := Some("io.github.dronegator.electricmind.Autocomplete")

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

//sourceManaged in Compile := file("sdsd")

//sourceManaged in Test := file("adasd")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")


name := "autocomplete"

version := "1.1"

scalaVersion := "2.10.0"

//mainClass := Option("ru.wordmetrix.WholeLotOfPictures")
mainClass := Some("io.github.dronegator.autocomplete.Autocomplete")

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0.M6" % "test"


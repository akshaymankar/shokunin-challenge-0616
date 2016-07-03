name := "shokunin-june"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.0" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")

mainClass in Compile := Some("Main")
    
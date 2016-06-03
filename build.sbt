name := "docs4s"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "bintray-ee" at "https://dl.bintray.com/equalexperts/uxforms-releases/"

libraryDependencies += "com.vspy" %% "mustache" % "1.3"

libraryDependencies += "org.scalatra.scalate" % "scalamd_2.11" % "1.6.1"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"
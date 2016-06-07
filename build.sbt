import _root_.bintray.Keys._
import _root_.bintray.Plugin._
import com.typesafe.sbt.SbtPgp._

import scala.util.Try

name := "docs4s"

version := Try(sys.env("LIB_VERSION")).getOrElse("1")

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

organization := "net.kenro-ji-jin"

libraryDependencies += "org.scalatra.scalate" % "scalamd_2.11" % "1.6.1"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }

pgpPassphrase := Some(Try(sys.env("SECRET")).getOrElse("goaway").toCharArray)

pgpSecretRing := file("./publish/sonatype.asc")

bintrayPublishSettings

repository in bintray := "repo"

bintrayOrganization in bintray := None

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/kingsleyh/docs4s"))

licenses +=("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra :=
  <scm>
    <url>git@github.com:kingsleyh/docs4s.git</url>
    <connection>scm:git:git@github.com:kingsleyh/docs4s.git</connection>
  </scm>
    <developers>
      <developer>
        <id>kingsleyh</id>
      </developer>
    </developers>

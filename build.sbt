name := "Phone Directory"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "tpolecat" at "http://dl.bintray.com/tpolecat/maven",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

lazy val doobieVersion = "0.2.2"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.2",
  "org.scalaz" %% "scalaz-effect" % "7.1.2",
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-contrib-postgresql" % doobieVersion,
  "org.tpolecat" %% "doobie-contrib-specs2"     % doobieVersion,
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.4" % "test"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

// Fork JVM when `run`-ing SBT
// http://stackoverflow.com/a/5265162/409976
fork in run := true
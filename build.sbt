name := "Phone Directory"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.scalaz" %% "scalaz-core" % "7.1.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

// Fork JVM when `run`-ing SBT
// http://stackoverflow.com/a/5265162/409976
fork in run := true
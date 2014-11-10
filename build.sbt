name := "spray-slick-api-example"

version := "1.0"

organization  := "spray.slick.example"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.11.2")

resolvers ++= Seq(
  "spray repo"         at "http://repo.spray.io/",
  "sonatype releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "typesafe repo"      at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  "io.spray"            %%  "spray-can"         % "1.3.2",
  "io.spray"            %%  "spray-routing"     % "1.3.2",
  "org.json4s"          %%  "json4s-native"     % "3.2.10",
  "org.json4s"          %%  "json4s-jackson"    % "3.2.10",
  "com.typesafe.akka"   %%  "akka-actor"        % "2.3.6",
  //"org.slf4j"           %   "slf4j-simple"      % "1.7.2",
  "com.typesafe.slick"  %%  "slick"             % "2.1.0",
  "com.h2database"      %   "h2"                % "1.4.182",
  "com.typesafe.akka"   %% "akka-slf4j"         % "2.3.6",
  "ch.qos.logback"      % "logback-classic"     % "1.0.13"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions += "-deprecation"

// fork a new JVM for 'run' and 'test:run'
fork := true

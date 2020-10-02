lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion    = "2.6.8"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "prisma.qr",
      scalaVersion    := "2.13.1"
    )),
    name := "cdl-poc",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.3",
      "org.apache.kafka"  % "kafka_2.13"                % "2.6.0",

      "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
      "com.lightbend.akka" %% "akka-stream-alpakka-file" % "2.0.2",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
      "com.typesafe.slick" %% "slick" % "3.3.3",
      "mysql"             % "mysql-connector-java"   % "6.0.6",

      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.0.8"         % Test
    ),
    mainClass in (Compile, run) := Option("com.closure.ClosurePoc")
  )

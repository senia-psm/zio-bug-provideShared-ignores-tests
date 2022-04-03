lazy val zioVersion      = "2.0.0-RC4"

lazy val root =
  project.in(file("."))
    .settings(
      scalaVersion := "2.13.8",
      libraryDependencies ++= Seq(
        "dev.zio" %% "zio"          % zioVersion,
        "dev.zio" %% "zio-test"     % zioVersion,
        "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
      ),
      testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    )

import sbt._
import Keys._

object BuildHelper {

  private val stdOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
  )

  private val std2xOptions = Seq(
    "-Xfatal-warnings",
    "-language:higherKinds",
    "-language:existentials",
    "-language:implicitConversions",
    "-explaintypes",
    "-Yrangepos",
    "-Xsource:2.13",
    "-Xlint:_,-type-parameter-shadow",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-deprecation:false",
  )

  private val optimizerOptions = Seq("-opt:l:inline", "-opt-inline-from:zio.internal.**")

  def extraOptions(scalaVersion: String) =
    CrossVersion.partialVersion(scalaVersion) match {
      case Some((2, 13)) =>
        std2xOptions ++ optimizerOptions
      case Some((2, 12)) =>
        Seq(
          "-opt-warnings",
          "-Ywarn-extra-implicit",
          "-Ywarn-unused:_,imports",
          "-Ywarn-unused:imports",
          "-opt:l:inline",
          "-opt-inline-from:zio.internal.**",
          "-Ypartial-unification",
          "-Yno-adapted-args",
          "-Ywarn-inaccessible",
          "-Ywarn-infer-any",
          "-Ywarn-nullary-override",
          "-Ywarn-nullary-unit",
        ) ++ std2xOptions ++ optimizerOptions
      case Some((2, 11)) =>
        Seq(
          "-Ypartial-unification",
          "-Yno-adapted-args",
          "-Ywarn-inaccessible",
          "-Ywarn-infer-any",
          "-Ywarn-nullary-override",
          "-Ywarn-nullary-unit",
          "-Xexperimental",
          "-Ywarn-unused-import",
        ) ++ std2xOptions
      case _ => Seq.empty
    }

  val stdSettings = Seq(
    scalacOptions            := stdOptions,
    crossScalaVersions       := Seq("2.13.8", "2.12.15", "3.1.1"),
    ThisBuild / scalaVersion := crossScalaVersions.value.head,
    scalacOptions            := stdOptions ++ extraOptions(scalaVersion.value),
    Test / parallelExecution := true,
    incOptions ~= (_.withLogRecompileOnMacro(false)),
    autoAPIMappings := true,
  )
}
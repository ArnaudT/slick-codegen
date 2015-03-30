name := "slickCodeGen"

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.0.0-RC2",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.0-RC2",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "com.zaxxer" % "HikariCP" % "2.3.2",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

slick <<= slickCodeGenTask

sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "main/slick").getPath
  val username = "user"
  val password = "password"
  val url = "jdbc:mysql://localhost/slick"
val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "com.example.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/" + "com/example/models" + "/Tables.scala"
  Seq(file(fname))
}
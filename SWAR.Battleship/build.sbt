name := "Battleship"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.8.5"
)     

play.Project.playJavaSettings

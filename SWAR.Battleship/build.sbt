name := "Battleship"

version := "1.0-SNAPSHOT"

resolvers += "db4o-repo" at "http://source.db4o.com/sbt"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.8.5",
  "com.google.inject" % "guice" % "3.0",
  "com.db4o" % "db4o-full-java5" % "8.0-SNAPSHOT"
)     

play.Project.playJavaSettings
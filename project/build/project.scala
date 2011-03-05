import sbt._
import reaktor.scct.ScctProject

class Project(info: ProjectInfo) extends DefaultWebProject(info) with ScctProject {

  val liftVersion = "2.2"
  val mavenLocal = "Local Maven Repository" at "file://D:/mvn-repository"

  override def outputDirectoryName = "target"
  override def managedDependencyPath = "project" / "lib_managed"
  override def compileOptions = Unchecked :: super.compileOptions.toList
  override def javaCompileOptions = JavaCompileOption("-Xmx256m -Xms64m -Xss4m") :: Nil
  override def testJavaCompileOptions = JavaCompileOption("-Xmx256m -Xms64m -Xss4m") :: Nil
  override def includeTest(s: String) = { s.endsWith("Spec") || 
                                          s.contains("UserGuide") || 
                                          s.matches("org.specs2.guide.*") || 
										  s.endsWith("Suite") || 
										  s.endsWith("Unit") }

  val specs2 		= "org.specs2" %% "specs2" % "1.0-SNAPSHOT" 
  val scalacheck 	= "org.scala-tools.testing" %% "scalacheck" % "1.8" 
  val testinterface = "org.scala-tools.testing" % "test-interface" % "0.5" 
  val scalazcore 	= "com.googlecode.scalaz" %% "scalaz-core" % "5.1-SNAPSHOT"
  val hamcrest      = "org.hamcrest" % "hamcrest-all" % "1.1"
  val mockito 	 	= "org.mockito" % "mockito-all" % "1.8.5" 
  val junit 		= "junit" % "junit" % "4.7"
  val parboiled     =  "org.parboiled" % "parboiled4j" % "0.9.9.0"
  val pegdown       =  "org.pegdown" % "pegdown" % "0.8.5.4"

  val webkit        =  "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default"
  val mapper        =  "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default"
  val h2            =  "com.h2database" % "h2" % "1.2.138"
  val jfreechart    =  "jfree" % "jfreechart" % "1.0.11"
  val jfreecommon   =  "jfree" % "jcommon" % "1.0.14"
  
  override val jettyPort = 8090
  
  def specs2Framework = new TestFramework("org.specs2.runner.SpecsFramework")
  override def testFrameworks = super.testFrameworks ++ Seq(specs2Framework)

  override def managedStyle = ManagedStyle.Maven
  override def defaultPublishRepository = {
    val nexusDirect = "http://nexus-direct.scala-tools.org/content/repositories/"
    if (version.toString.endsWith("SNAPSHOT")) 
	  Some("scala-tools snapshots" at nexusDirect + "snapshots/")
	else
	  Some("scala-tools releases" at nexusDirect + "releases/")
  }
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)

  val releasesRepo = "releases-repo" at "http://scala-tools.org/repo-releases"
  val snapshotsRepo = "snapshots-repo" at "http://scala-tools.org/repo-snapshots"
  val specsRepo = "specs-repo" at "http://specs.googlecode.com/svn/maven2"

}
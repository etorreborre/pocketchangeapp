package acceptance

import org.specs2._
import html._

/**
 */
trait Application {
  val driver: DriverApp

  type A <: Application

  implicit def copyDriver(d: DriverApp): A
  implicit def toDriver(a: A): DriverApp = a.driver
  def args(debug: Boolean) = copyDriver(driver.args(debug))
}

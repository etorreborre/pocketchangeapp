package org.specs2
package html

import form._

/**
 * Drivers apps for the driver actions
 */
case class HtmlUnitApp(driver: Driver = HtmlUnitDriver()) extends DriverApp {
  type D = HtmlUnitApp
  def copyDriver(d: Driver): HtmlUnitApp = HtmlUnitApp(d)
}

case class FirefoxApp(driver: Driver = FirefoxDriver()) extends DriverApp {
  type D = FirefoxApp
  def copyDriver(d: Driver): FirefoxApp = FirefoxApp(d)
}

case class InternetExplorerApp(driver: Driver = InternetExplorerDriver()) extends DriverApp {
  type D = InternetExplorerApp
  def copyDriver(d: Driver): InternetExplorerApp = InternetExplorerApp(d)
}

case class ChromeApp(driver: Driver = ChromeDriver()) extends DriverApp {
  type D = ChromeApp
  def copyDriver(d: Driver): ChromeApp = ChromeApp(d)
}


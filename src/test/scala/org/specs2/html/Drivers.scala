package org.specs2
package html

import org.openqa.selenium._
import com.gargoylesoftware.htmlunit.{DefaultCredentialsProvider, WebClient}

case class HtmlUnitDriver(driver: WebDriver = new htmlunit.HtmlUnitDriver, args: DriverArgs = DriverArgs()) extends Driver {
  def copyDriver(a: DriverArgs) = copy(args = a)
  override def authenticate(name: String, password: String) = copy(driver = new htmlunit.HtmlUnitDriver {
      protected override def modifyWebClient(client: WebClient) =
        setCredentials(client, name, password)
    })
}
case class FirefoxDriver(driver: WebDriver = new firefox.FirefoxDriver, args: DriverArgs = DriverArgs()) extends Driver {
  def copyDriver(a: DriverArgs) = copy(args = a)
}
case class InternetExplorerDriver(driver: WebDriver = new ie.InternetExplorerDriver, args: DriverArgs = DriverArgs()) extends Driver {
  def copyDriver(a: DriverArgs) = copy(args = a)
}
case class ChromeDriver(driver: WebDriver = new chrome.ChromeDriver, args: DriverArgs = DriverArgs()) extends Driver {
  def copyDriver(a: DriverArgs) = copy(args = a)
}

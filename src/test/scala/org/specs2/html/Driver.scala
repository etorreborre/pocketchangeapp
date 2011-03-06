package org.specs2
package html

import scala.collection.JavaConversions._
import org.openqa.selenium._
import control.Exceptions._
import execute._
import StandardResults._
import com.gargoylesoftware.htmlunit.{WebClient, DefaultCredentialsProvider}

trait Driver {
  val driver: WebDriver
  implicit val args: DriverArgs

  def authenticate(name: String, password: String): Driver = {
    debug("authenticating with "+name+"/"+password)
    this
  }

  protected def setCredentials(client: WebClient, name: String, password: String) = {
    val creds = new DefaultCredentialsProvider
    creds.addCredentials(name, password)
    client.setCredentialsProvider(creds)
    client
  }

  def args(debug: Boolean) = copyDriver(DriverArgs(debug))

  def copyDriver(args: DriverArgs): Driver

  def clickOn(name: String): Result = {
    debug("clicking on " + name)
    action(driver.findElement(By.linkText(name)).click)
  }

  def goto(url: String) = {
    debug("going to " + url)
    action(driver.get(url))
  }

  def pageSource = {
    debug("getting page source")
    driver.getPageSource()
  }

  def setIdKeys(keys: Pair[String, String]*) = {
    debug("setting id " + keys)
    keys foreach { case (k, v) => setValue(By.id(k), v) }
  }
  def setXpathKeys(keys: Pair[String, String]*) = {
    debug("setting xpath " + keys)
    keys foreach { case (k, v) => setValue(By.xpath(k), v) }
  }

  def setNameKeys(keys: Pair[String, String]*) = {
    debug("setting name " + keys)
    keys foreach { case (k, v) => setValue(By.name(k), v) }
  }

  def setClassNameKeys(keys: Pair[String, String]*) = {
    debug("class name " + keys)
    keys foreach { case (k, v) => setValue(By.className(k), v) }
  }

  def setTagNameKeys(keys: Pair[String, String]*) = {
    debug("tag name " + keys)
    keys foreach { case (k, v) => setValue(By.tagName(k), v) }
  }

  def setCssSelectorKeys(keys: Pair[String, String]*) = {
    debug("setting css selector " + keys)
    keys foreach { case (k, v) => setValue(By.cssSelector(k), v) }
  }
  def submit = {
    debug("submitting")
    action { debug(driver.getPageSource); driver.findElement(By.xpath("//input[@type='submit']")).submit }
  }

  def hasLink(name: String): Result = {
    debug("looking for link "+name)
    debug("current page is\n"+driver.getPageSource)
    action(driver.findElement(By.linkText(name)))
  }

  def textByTag(name: String): Option[String] = {
    debug("looking for tag "+name)
    tryo(driver.findElement(By.tagName(name)).getText)
  }

  def textsByTag(name: String): List[String] = {
    debug("looking for tag "+name)
    tryOr(driver.findElements(By.tagName(name)).toList.map(_.getText))(e => List())
  }

  def textByXPath(path: String): Option[String] = {
    debug("looking for xpath "+path)
    tryo(driver.findElement(By.xpath(path)).getText)
  }

  def textsByXPath(path: String): List[String] = {
    debug("looking for xpath "+path)
    debug(tryOr(driver.findElements(By.xpath(path)).toList.map(_.getText))(e => List()))
  }

  private def action[T](a: =>T): Result = {
    tryOr({ a; (success:Result) })(Error(_))
  }

  private def setValue(by: By, v: String) = {
    driver.findElements(by).foreach { e =>
      e.clear()
      e.sendKeys(v)
    }
  }

  protected def debug[T](message: T)(implicit args: DriverArgs = DriverArgs()) : T = {
    if (args.debug)
      println(message)
    message
  }
}
case class DriverArgs(debug: Boolean = false)

package org.specs2
package html

import matcher.{Matcher, MustMatchers}
import execute.Result

trait DriverApp extends MustMatchers {
  val driver: Driver

  type D <: DriverApp
  
  def copyDriver(driver: Driver): D
  
  def authenticate(name: String, password: String): D = copyDriver(driver.authenticate(name, password))

  def args(debug: Boolean) = copyDriver(driver.args(debug))

  implicit def toTuple3(t: ((String, String), String)): (String, String, String) = (t._1._1, t._1._2, t._2)

  def clickOn(name: String): D = clickOn("click on", name)
  def clickOn(label: String, name: String): D = {
    driver.clickOn(name)
    copyDriver(driver)
  }

  def goto(url: String): D = goto("goto", url)
  def goto(label: String, url: String): D = {
    driver.goto(url)
    copyDriver(driver)
  }

  def textsByXPath(path: String): Seq[String] ={
    driver.textsByXPath(path)
  }

  def setId(label: String, key: String, value: String) = idField(label, key, value)
  def setId(lkv: (String, String, String), lkvs: (String, String, String)*) = {
    (idField _).tupled(lkv)
  }

  def setXpath(label: String, key: String, value: String) = xpathField(label, key, value)
  def setXpath(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setXpath(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setXpath(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    (xpathField _).tupled(lkv)
    copyDriver(driver)
  }

  def setName(label: String, key: String, value: String) = nameField(label, key, value)
  def setName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    (nameField _).tupled(lkv)
    copyDriver(driver)
  }

  def setClassName(label: String, key: String, value: String) = classNameField(label, key, value)
  def setClassName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setClassName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setClassName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    (classNameField _).tupled(lkv)
    copyDriver(driver)
  }

  def setTagName(label: String, key: String, value: String) = tagNameField(label, key, value)
  def setTagName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setTagName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setTagName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    (tagNameField _).tupled(lkv)
  }

  def setCssSelector(label: String, key: String, value: String) = cssSelectorField(label, key, value)
  def setCssSelector(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setCssSelector(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setCssSelector(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    (cssSelectorField _).tupled(lkv)
  }

  def submit: D = submit("submit")
  def submit(label: String): D = {
    driver.submit
    copyDriver(driver)
  }

  def hasLink(l: String, name: String) = {
    driver.hasLink(name) must beSuccessful
  }

  def contentMatch(url: String, matcher: Matcher[String]) = {
    goto(url).
    driver.pageSource must matcher
  }

  def hasTagText(l: String, name: String, text: String): Result = {
    driver.textsByTag(name) must contain(text)
  }

  protected def idField(label: String, key: String, value: String) = {
    driver.setIdKeys(key -> value)
    copyDriver(driver)
  }
  protected def xpathField(label: String, key: String, value: String) = {
    driver.setXpathKeys(key -> value)
    copyDriver(driver)
  }
  protected def nameField(label: String, key: String, value: String) = {
    driver.setNameKeys(key -> value)
    copyDriver(driver)
  }
  protected def cssSelectorField(label: String, key: String, value: String) = {
    driver.setCssSelectorKeys(key -> value)
    copyDriver(driver)
  }
  protected def tagNameField(label: String, key: String, value: String) = {
    driver.setTagNameKeys(key -> value)
    copyDriver(driver)
  }

  protected def classNameField(label: String, key: String, value: String) = {
    driver.setClassNameKeys(key -> value)
    copyDriver(driver)
  }
}
package org.specs2
package html

import matcher.MustMatchers
import specification.Forms
import form. {Cell, Form, Field, FieldCell, Decorator}

trait DriverForm extends MustMatchers with Forms {
  val driver: Driver
  val form: Form

  type D <: DriverForm
  
  def copy(form: Form): D
  def copyDriver(driver: Driver): D

  def args(debug: Boolean) = copyDriver(driver.args(debug))

  implicit def toTuple3(t: ((String, String), String)): (String, String, String) = (t._1._1, t._1._2, t._2)

  def toEffect(label: String) = form.toEffect(label).bkWhiteLabel

  def toProp(label: String) = form.toProp(label).bkWhiteLabel
  def toProp(label: String, expected: String) = (form.toProp(label)(expected)).bkWhiteLabel

  def tr(cell: Cell, cells: Cell*) = copy(form.tr(cell, cells:_*))
  def th(h1: Field[_], hs: Field[_]*) = copy(form.th(h1, hs:_*))
  def th(h1: String, hs: String*) = copy(form.th(h1, hs:_*))
  def tr(f: Form) = copy(form.tr(f))
  def trs[T](ts: Iterable[T])(f: T => Form) = ts.foldLeft(this) { (res, cur) =>  res.tr(f(cur)) }

  def clickOn(name: String): D = clickOn("click on", name)
  def clickOn(label: String, name: String): D = {
    lazy val action = { driver.clickOn(name); name }
    tr(field(label, action))
  }

  def goto(url: String): D = goto("goto", url)
  def goto(label: String, url: String): D = {
    lazy val action = { driver.goto(url); url }
    tr(field("goto", action))
  }

  def textsByXPath(path: String): Seq[String] = driver.textsByXPath(path)

  def setId(label: String, key: String, value: String) = tr(idField(label, key, value))
  def setId(lkv: (String, String, String), lkvs: (String, String, String)*) = {
    tr((idField _).tupled(lkv), (lkvs map (idField _).tupled) map (FieldCell(_)):_*)
  }

  def setXpath(label: String, key: String, value: String) = tr(xpathField(label, key, value))
  def setXpath(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setXpath(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setXpath(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    tr((xpathField _).tupled(lkv), (lkvs map (xpathField _).tupled) map (FieldCell(_)):_*)
  }

  def setName(label: String, key: String, value: String) = tr(nameField(label, key, value))
  def setName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    tr((nameField _).tupled(lkv), (lkvs map (nameField _).tupled) map (FieldCell(_)):_*)
  }

  def setClassName(label: String, key: String, value: String) = tr(classNameField(label, key, value))
  def setClassName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setClassName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setClassName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    tr((classNameField _).tupled(lkv), (lkvs map (classNameField _).tupled) map (FieldCell(_)):_*)
  }

  def setTagName(label: String, key: String, value: String) = tr(tagNameField(label, key, value))
  def setTagName(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setTagName(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setTagName(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    tr((tagNameField _).tupled(lkv), (lkvs map (tagNameField _).tupled) map (FieldCell(_)):_*)
  }

  def setCssSelector(label: String, key: String, value: String) = tr(cssSelectorField(label, key, value))
  def setCssSelector(lkv: ((String, String), String), lkvs: ((String, String), String)*): D = setCssSelector(toTuple3(lkv), (lkvs map toTuple3):_*)
  def setCssSelector(lkv: (String, String, String), lkvs: (String, String, String)*): D = {
    tr((cssSelectorField _).tupled(lkv), (lkvs map (cssSelectorField _).tupled) map (FieldCell(_)):_*)
  }

  def submit: D = submit("submit")
  def submit(label: String, d: Decorator = Decorator()): D = {
    tr(effect(label, driver.submit).copy(decorator = d))
  }

  def hasLink(l: String, name: String) = {
    tr(prop(l, "success").resultIs(driver.hasLink(name) must beSuccessful)("success"))
  }
  def hasTagText(l: String, name: String, text: String) = {
    tr(prop(l, "success").resultIs(driver.textsByTag(name) must contain(text))("success"))
  }

  protected def idField(label: String, key: String, value: String) = {
    lazy val action = { driver.setIdKeys(key -> value); value }
    field(label, action)
  }
  protected def xpathField(label: String, key: String, value: String) = {
    lazy val action = { driver.setXpathKeys(key -> value); value }
    field(label, action)
  }
  protected def nameField(label: String, key: String, value: String) = {
    lazy val action = { driver.setNameKeys(key -> value); value }
    field(label, action)
  }
  protected def cssSelectorField(label: String, key: String, value: String) = {
    lazy val action = { driver.setCssSelectorKeys(key -> value); value }
    field(label, action)
  }
  protected def tagNameField(label: String, key: String, value: String) = {
    lazy val action = { driver.setTagNameKeys(key -> value); value }
    field(label, action)
  }

  protected def classNameField(label: String, key: String, value: String) = {
    lazy val action = { driver.setClassNameKeys(key -> value); value }
    field(label, action)
  }
}
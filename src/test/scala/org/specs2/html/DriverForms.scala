package org.specs2
package html

import form._

/**
 * Drivers and forms for the driver actions
 */
case class HtmlUnitForm(form: Form = Form(), driver: Driver = HtmlUnitDriver()) extends DriverForm {
  type D = HtmlUnitForm
  def copy(f: Form): HtmlUnitForm = HtmlUnitForm(f, driver)
  def copyDriver(d: Driver): HtmlUnitForm = HtmlUnitForm(form, d)
}
case object HtmlUnitForm {
  def apply(title: String) = new HtmlUnitForm(Form.th(title))
}

case class FirefoxForm(form: Form = Form(), driver: Driver = FirefoxDriver()) extends DriverForm {
  type D = FirefoxForm
  def copy(f: Form): FirefoxForm = FirefoxForm(f, driver)
  def copyDriver(d: Driver): FirefoxForm = FirefoxForm(form, d)
}
case object FirefoxForm {
  def apply(title: String) = new FirefoxForm(Form.th(title))
}

case class InternetExplorerForm(form: Form = Form(), driver: Driver = InternetExplorerDriver()) extends DriverForm {
  type D = InternetExplorerForm
  def copy(f: Form): InternetExplorerForm = InternetExplorerForm(f, driver)
  def copyDriver(d: Driver): InternetExplorerForm = InternetExplorerForm(form, d)
}
case object InternetExplorerForm {
  def apply(title: String) = new InternetExplorerForm(Form.th(title))
}

case class ChromeForm(form: Form = Form(), driver: Driver = ChromeDriver()) extends DriverForm {
  type D = ChromeForm
  def copy(f: Form): ChromeForm = ChromeForm(f, driver)
  def copyDriver(d: Driver): ChromeForm = ChromeForm(form, d)
}
case object ChromeForm {
  def apply(title: String) = new ChromeForm(Form.th(title))
}


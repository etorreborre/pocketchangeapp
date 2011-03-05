package org.specs2
package html

import org.openqa.selenium.WebElement

trait WebElementx {
  implicit def toElem(w: WebElement): WebElementXml = new WebElementXml(w)
  class WebElementXml(w: WebElement) {
    import scala.xml._
    def toElem = Elem(null, w.getTagName, Null, TopScope, Text(w.getText))
    def toXmlString = new WebElementXml(w).toElem.toString
  }
}

object WebElementx extends WebElementx
package acceptance

import org.specs2._
import form.{Form, Cell, Field  }
import html.DriverForm
import specification.Forms

/**
 */
trait ApplicationForm extends Forms {
  val driverForm: DriverForm
  val form: Form

  type A <: ApplicationForm

  implicit def copyDriverForm(d: DriverForm): A
  implicit def copyForm(f: Form): A
  implicit def toDriverForm(a: A): DriverForm = a.driverForm
  def args(debug: Boolean) = copyDriverForm(driverForm.args(debug))

  def newForm(t: String): A = copyForm(Form(t))
  def th(t: String, ts: String*) = copyForm(form.th(t, ts))
  def th(h1: Field[_], hs: Field[_]*) = copyForm(form.th(h1, hs))
  def tr(cell: Cell, cells: Cell*) = copyForm(form.tr(cell, cells:_*))
  def tr(f: =>Form) = copyForm(form.tr(f))
  def trProp(label: String, a: A) = tr(a.toProp(label))
  def trEffect(label: String, a: A) = tr(a.toEffect(label))


}

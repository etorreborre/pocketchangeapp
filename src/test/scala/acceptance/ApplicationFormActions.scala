package acceptance

import org.specs2._
import form.Form
import html.DriverForm
import specification.Forms

trait PocketChangeFormsActions extends PocketChangeFormsObservations {

  def homepage: PocketChangeForms = driverForm.
    goto("http://localhost:8090")

  def signup(email: String, password: String): PocketChangeForms =
    homepage.
    clickOn("Sign Up").
    setId("First name", "txtFirstName", "Eric").
    setId("Last name", "txtLastName", "Torreborre").
    setId("Email", "txtEmail", email).
    tr(field("Locale", "English (Australia)")).
    tr(field("Time Zone", "Australia/Sydney")).
    setXpath("password" -> "//input[@type='password']" -> password).
    submit("submit")

  def login(email: String, password: String): PocketChangeForms =
    homepage.
    clickOn("Login").
    setName("Email address", "username", email).
    setName("Password", "password", password).
    submit("login")

  def logoutAction: PocketChangeForms = driverForm.clickOn("Logout")

  def lost(email: String): PocketChangeForms = driverForm.
    clickOn("Lost Password").
    setXpath("Email" -> "//input[@type='text']" -> email).
    submit("Send It")

  def changeThePassword(oldPassword: String, newPassword: String): PocketChangeForms = driverForm.
    clickOn("Change Password").
    setXpath("Old password" -> "(//input[@type='password'])[1]" -> oldPassword).
    setXpath("New password" -> "(//input[@type='password'])[2]" -> newPassword).
    setXpath("Repeat password" -> "(//input[@type='password'])[3]" -> newPassword).
    submit("change")


  def editUserDetails(email: String): PocketChangeForms = driverForm.
    clickOn("Edit User").
    setId("First name", "txtFirstName", "Eric").
    setId("Last name", "txtLastName", "Torreborre").
    setId("Email", "txtEmail", email).
    tr(field("Locale", "English (Australia)")).
    tr(field("Time Zone", "Australia/Sydney")).
    submit("edit")

  def addANewAccount(name: String, description: String): PocketChangeForms =
      login("me@dot.com", "password").
      manageAccounts.
      addAnAccount(name, description)

  def manageAccounts: PocketChangeForms = driverForm.clickOn("Manage Accounts")

  def addAnAccount(name: String, description: String): PocketChangeForms = driverForm.
    clickOn("Add Account").
    setXpath("Name" -> "(//input[@type='text'])[1]" -> name).
    setXpath("Description" -> "(//input[@type='text'])[2]" -> description).
    submit("Save")

}

trait PocketChangeFormsObservations extends ApplicationForm {

  type A = PocketChangeForms

  def message(msg: String): PocketChangeForms = driverForm.hasTagText(msg, "li", msg)
  def userCanLogout: PocketChangeForms = driverForm.hasLink("The user can log out", "Logout")

  def accounts: Seq[Account] = {
    Accounts(driverForm.textsByXPath("//div[@id='entryform']//td[position() = 1]") zip
             driverForm.textsByXPath("//div[@id='entryform']//td[position() = 2]"))
  }

  object Accounts {
    def apply(namesAndDescriptions: Seq[(String, String)]) =  namesAndDescriptions.map(nd => Account(nd._1, nd._2))
  }
  case class Account(name: String, description: String) {
    def form = Form.tr(field(name), field(description))
  }
}


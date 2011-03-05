package acceptance

import org.specs2._
import matcher._

trait PocketChangeActions extends PocketChangeObservations {

  def homepage: PocketChange = driver.
    goto("http://localhost:"+Server.PORT)

  def signup(email: String, password: String): PocketChange =
    homepage.
    clickOn("Sign Up").
    setId("First name", "txtFirstName", "Eric").
    setId("Last name", "txtLastName", "Torreborre").
    setId("Email", "txtEmail", email).
    setXpath("password" -> "//input[@type='password']" -> password).
    submit("submit")

  def authenticate(email: String, password: String): PocketChange = copyDriver(driver.authenticate(email, password))

  def login(email: String, password: String): PocketChange =
    homepage.
    clickOn("Login").
    setName("Email address", "username", email).
    setName("Password", "password", password).
    submit("login")

  def logoutAction: PocketChange = driver.clickOn("Logout")

  def lost(email: String): PocketChange = driver.
    clickOn("Lost Password").
    setXpath("Email" -> "//input[@type='text']" -> email).
    submit("Send It")

  def changeThePassword(oldPassword: String, newPassword: String): PocketChange = driver.
    clickOn("Change Password").
    setXpath("Old password" -> "(//input[@type='password'])[1]" -> oldPassword).
    setXpath("New password" -> "(//input[@type='password'])[2]" -> newPassword).
    setXpath("Repeat password" -> "(//input[@type='password'])[3]" -> newPassword).
    submit("change")


  def editUserDetails(email: String): PocketChange = driver.
    clickOn("Edit User").
    setId("First name", "txtFirstName", "Eric").
    setId("Last name", "txtLastName", "Torreborre").
    setId("Email", "txtEmail", email).
    submit("edit")

  def addANewAccount(name: String, description: String): PocketChange =
      login("me@dot.com", "password").
      manageAccounts.
      addAnAccount(name, description)

  def manageAccounts: PocketChange = driver.clickOn("Manage Accounts")

  def addAnAccount(name: String, description: String): PocketChange = driver.
    clickOn("Add Account").
    setXpath("Name" -> "(//input[@type='text'])[1]" -> name).
    setXpath("Description" -> "(//input[@type='text'])[2]" -> description).
    submit("Save")
}

trait PocketChangeObservations extends Application with Expectations {

  type A = PocketChange

  def message(msg: String) = driver.hasTagText(msg, "li", msg)
  def userCanLogout = driver.hasLink("The user can log out", "Logout")

  def get(url: String, matcher: Matcher[String]) = driver.contentMatch(url, matcher)

  def accounts: Seq[Account] = {
    Accounts(driver.textsByXPath("//div[@id='entryform']//td[position() = 1]") zip
             driver.textsByXPath("//div[@id='entryform']//td[position() = 2]"))
  }

  object Accounts {
    def apply(namesAndDescriptions: Seq[(String, String)]) =  namesAndDescriptions.map(nd => Account(nd._1, nd._2))
  }
  case class Account(name: String, description: String)
}


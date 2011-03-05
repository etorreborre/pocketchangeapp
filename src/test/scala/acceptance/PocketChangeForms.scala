package acceptance

import org.specs2._
import form. {TextCell, Form}
import html._

/**
 * This class creates forms displaying high-level actions which are implemented by using a DriverForm for the precise
 * interactions with the web application
 */
case class PocketChangeForms(form: Form = Form(), driverForm: DriverForm = new HtmlUnitForm()) extends PocketChangeFormsActions {

  implicit def copyDriverForm(d: DriverForm): A = copy(driverForm = d)
  implicit def copyForm(f: Form): A = copy(form = f)

  def signupWith(email: String, password: String) =
    tr(signup(email, password).toEffect("Signup with"),
       field("email", email),
       field("password", password))

  def messageIs(msg: String) = tr(message(msg).toProp("The message is", msg))

  def userIsLoggedIn = trProp("The user must be logged in", userCanLogout)

  def logout = trEffect("Logout", logoutAction)

  def loginWith(email: String, password: String) =
    tr(login(email, password).toEffect("Login with"),
       field("email", email),
       field("password", password))

  def getLostPasswordFor(email: String) =
    tr(lost(email).toEffect("Ask for lost password with"),
       field("email", email))

  def changePassword(oldPassword: String, newPassword: String) =
    tr(changeThePassword(oldPassword, newPassword).toEffect("Change password"),
       field("old password", oldPassword),
       field("new password", newPassword))

  def editDetails(email: String) = trEffect("Edit User", editUserDetails(email))

  def addAccount(name: String, description: String) =
    tr(addANewAccount(name, description).toEffect("Add account"),
       field("name", name),
       field("description", description))

  def accountsAre(namesAndDescriptions: (String, String)*) = {
    tr(Form.
       tr(field("Expected accounts").bkWhite.bold.center).
       th("name", "description").
       subset(Accounts(namesAndDescriptions), accounts).inline)
  }
}


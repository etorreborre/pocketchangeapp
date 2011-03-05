package acceptance

import org.specs2._
import html._

/**
 * This class provides high-level actions which are implemented by using a Driver for the precise
 * interactions with the web application
 */
case class PocketChange(driver: DriverApp = new HtmlUnitApp()) extends PocketChangeActions {

  def copyDriver(d: DriverApp) = copy(driver = d)

  def signupWith(email: String, password: String) = signup(email, password)

  def messageIs(msg: String) = message(msg)

  def userIsLoggedIn = userCanLogout

  def logout = logoutAction

  def authenticateWith(email: String, password: String) = authenticate(email, password)

  def loginWith(email: String, password: String) = login(email, password)

  def getLostPasswordFor(email: String) = lost(email)

  def changePassword(oldPassword: String, newPassword: String) = changeThePassword(oldPassword, newPassword)

  def editDetails(email: String) = editUserDetails(email)

  def addAccount(name: String, description: String) = addANewAccount(name, description)

}


package acceptance

import org.specs2._
import runner.JUnitRunner
import specification._
import org.junit.runner.RunWith

/**
 * This specification shows how to specify a web applications using specs2 and Forms (@see org.specs2.guide.Forms).
 * It tries to show best practises in terms of readibility and maintenance.
 *
 * The specification is:
 *
 *  * using the `literate` Arguments so that
 *    * there is no automatic indentation
 *    * the execution is sequential
 *  * using a `Step` to cleanup the database before executing actions on the application
 *  * using other `Step` actions to reset the application state (logging the user for example) without displaying the actions
 *
 * The functionalities for specifying the application behavior are layered in distinct traits and classes. The first 2
 * traits can be reused for any web application specification:
 *
 *  * the `Driver` trait is used interact with the website. It is responsible of clicking on links, entering inputs,
 *    checking the presence of links,... It is just a wrapper on the WebDriver classes from the Selenium project. The
 *    `HtmlUnitDriver` is an implementation of that trait using the `org.openqa.selenium.htmlunit.HtmlUnitDriver` class.
 *
 *  * the `DriverForm` trait is using the Driver trait. It creates a Form object where each elementary interactions
 *    with a web page is represented on a distinct row with a label defining the action.
 *
 *    For example the `clickOn(link)` method finds a link on the page and adds on a new row an `Effect` cell which,
 *    when executed, will find the link WebElement and click on it.
 *
 *    The forms created by this trait are usually not displayed because they describe low-level interactions with the web
 *    page. They will be only displayed when the specification fails to help with debugging.
 *
 *    The `HtmlUnitForm` class is an implementation of that trait using an `HtmlUnitDriver` class.
 *
 * The next traits and classes are specific to the application being specified:
 *
 *  * the `ApplicationActions` trait is declaring all the possible user actions without exposing the details of their
 *    implementation in terms of web page interactions. It uses the `HtmlUnitForm` class to effectively create actions.
 *
 *    For example the `loginWith(email, password)` action is implemented by creating a Form which enters the email/password
 *    in the right fields and submits the html form.
 *
 *  * the `ApplicationObservations` trait defines checks which can be done using the application. For example `userIsLoggedIn`
 *    is something that is observable by the user. It is actually implemented using the `HtmlUnitForm` class by checking the
 *    presence of the `Logout` link.
 *
 *  * the `ApplicationForms` class mixes the `ApplicationActions` and `ApplicationObservations` traits to create a high level
 *    Form showing user-level actions like `loginWith(email, password)`. If the action fails, the underlying `DriverForm`
 *    implementing all the interactions with the page is displayed to show the failing ones.
 *
 * Note that this strategy of nesting forms to hide implementation details can be also done at the application level to
 * encapsulate high-level actions with low-level ones.
 */
@RunWith(classOf[JUnitRunner])
class SignupAndLoginSpec extends SpecificationWithJUnit { def is = literate^
  "Sign-up and login process".title^
                                                                                                                        """
### Signing up

The user can sign up to the *PocketChange* application to create a user name and login.

He does so by providing his email address, a password and other personal details. If everything is filled in, a User object
is created and the user is logged in:
                                                                                                                        """^
  Step(setup)                                                                                                           ^
                                                                                                                        p^
  pocketChange.
  newForm("Sign up").
  signupWith("me@dot.com", "password").
  userIsLoggedIn                                                                                                        ^
                                                                                                                        """
### Login in

Then the user can log in by providing his email address and password:
                                                                                                                        """^
  Step(pocketChange.logout.execute)                                                                                      ^
  pocketChange.
  newForm("Login").
  loginWith("me@dot.com", "password").
  userIsLoggedIn                                                                                                        ^
                                                                                                                        """
If the email or password is not correct there is an error message:
                                                                                                                        """^
  pocketChange.
  newForm("Login error").
  logout.
  loginWith("me@dot.com", "wrong").
  messageIs("Invalid Username/Password")                                                                                ^
                                                                                                                        """
### Lost password

If the user has lost his password he can recover it by entering his email address. He will then receive an email resetting
his password with a new one:
                                                                                                                        """^
  pocketChange.
  newForm("Lost password").
  getLostPasswordFor("me@dot.com").
  messageIs("Password Reset Email sent")                                                                                ^
                                                                                                                        """
### Changing the password

The user can change his password by entering the old one and a new one. If the new password is accepted, a message is displayed
and the user can login with his new password:
                                                                                                                        """^
  Step(pocketChange.loginWith("me@dot.com", "password").execute)                                                         ^
  pocketChange.
  newForm("Change password").
  changePassword("password", "password2").
  messageIs("Password Changed").
  logout.
  loginWith("me@dot.com", "password2").
  userIsLoggedIn                                                                                                        ^
                                                                                                                        """
### Changing the user details

The user can change his details, first name, last name and his email address. If he changed his email address he can logout
and login again using that address:
                                                                                                                        """^
  pocketChange.
  newForm("Edit user").
  editDetails("me2@dot.com").
  messageIs("You have updated your profile").
  logout.
  loginWith("me2@dot.com", "password2").
  userIsLoggedIn                                                                                                        ^
                                                                                                                        p^
  Step(teardown)                                                                                                        ^
                                                                                                                        end

  val pocketChange = PocketChangeForms()
  def setup = {
    Server.start
    Database.cleanup
  }
  def teardown = Server.stop

}

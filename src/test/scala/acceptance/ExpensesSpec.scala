package acceptance

import org.specs2._
import runner.JUnitRunner
import specification._
import org.junit.runner.RunWith
import com.pocketchangeapp.model.User

@RunWith(classOf[JUnitRunner])
class ExpensesSpec extends SpecificationWithJUnit { def is = literate^
  "Expenses".title^
                                                                                                                        """
### Creating accounts

The user has to create an Account to be able to enter expenses.

To do so he provides the account name and a description:
                                                                                                                        """^
  Step(Database.cleanup)                                                                                             ^
  Step(Database.saveUser)                                                                                             ^
                                                                                                                        p^
  pocketChange.
  newForm("Account creation").
  addAccount("books", "tech books").
  addAccount("coffee", "expressos").
  accountsAre("books" -> "tech books",
              "coffee" -> "expressos")                                                                                  ^
                                                                                                                        end

  val pocketChange = PocketChangeForms().args(debug=true)

}
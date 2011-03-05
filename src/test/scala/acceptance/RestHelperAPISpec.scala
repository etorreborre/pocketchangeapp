package acceptance

import org.specs2._
import html._
import runner.JUnitRunner
import specification.Step
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class RestHelperAPISpec extends SpecificationWithJUnit { def is =
  Step(Server.start)^
                                                                                                       """
The REST API serves Expenses and Accounts as XML and Atom documents.
                                                                                                       """^
  "The REST API for Expenses provides"                                                                 ^
    "A GET operation: /api/expense/id to get an Expense as XML"                                        ! GET().e1^
                                                                                                       p^
                                                                                                       end

  case class GET() extends API {

    def e1 = {
      pocketchange.authenticateWith(user.email, "password").
        get("http://localhost:"+Server.PORT+"/api/expense/"+expense.id, contain("hello"))
    }.pendingUntilFixed("need to find a proper REST api")
  }
  trait API {
    val driver = new HtmlUnitDriver
    lazy val pocketchange = PocketChange()
  }

  lazy val user = Database.user
  lazy val expense = Database.expense
}
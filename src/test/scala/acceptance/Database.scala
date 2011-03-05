package acceptance

import java.text.SimpleDateFormat
import com.pocketchangeapp.model._
import net.liftweb.mapper.{By, NotNullRef}

object Database {
  implicit def dateformat = new SimpleDateFormat("dd/MM/yyyy").parse(_:String)

  def cleanup = {
    User.bulkDelete_!!(NotNullRef(User.id))
    Account.bulkDelete_!!(NotNullRef(Account.id))
    Expense.bulkDelete_!!(NotNullRef(Expense.id))
  }

  lazy val user = saveUser
  def saveUser = {
    val u = createUser
    u.save
    u
  }

  def createUser = {
    User.create.firstName("Eric").
                lastName("Torreborre").
                locale("en_AU").
                timezone("Australia/Sydney").
                email("me@dot.com").
                password("password").
                validated(true)
  }


  lazy val expense = saveExpense

  def saveExpense = {
    val e = createExpense
    e.save
    e
  }

  def createExpense: Expense = Expense.create.dateOf("01/01/2011").amount(100.0).
                              description("expense").
                              account(createAccount).
                              tags(List(Tag.create))

  lazy val account = saveAccount

  def saveAccount = {
    val a = createAccount
    a.save
    a
  }

  def createAccount: Account = Account.create.owner(createUser).
                                       name("Account1").
                                       description("fun")
}
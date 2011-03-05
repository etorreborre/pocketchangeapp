package acceptance

import java.text.SimpleDateFormat
import com.pocketchangeapp.model._
import net.liftweb.mapper.{By, NotNullRef}

object Database {
  def cleanup = {
    User.bulkDelete_!!(NotNullRef(User.id))
    Account.bulkDelete_!!(NotNullRef(Account.id))
    Expense.bulkDelete_!!(NotNullRef(Expense.id))
  }

  lazy val user = saveUser
  def saveUser = {
    val u = User.create.firstName("Eric").
                lastName("Torreborre").
                locale("en_AU").
                timezone("Australia/Sydney").
                email("me@dot.com").
                password("password").
                validated(true)
    u.save
    u
  }

  implicit def dateformat = new SimpleDateFormat("dd/MM/yyyy").parse(_:String)

  lazy val expense = saveExpense

  def saveExpense = {
    val e = Expense.create.dateOf("01/01/2011").amount(100.0).
                   description("expense").
                   account(account).
                   tags(List(Tag.create))
    e.save
    e
  }

  lazy val account = saveAccount
  def saveAccount = {
    val a = Account.create.owner(user).
                           name("Account1").
                           description("fun")
    a.save
    a
  }
}
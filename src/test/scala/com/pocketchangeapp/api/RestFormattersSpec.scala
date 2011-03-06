package com.pocketchangeapp
package api

import _root_.acceptance.Database
import org.specs2._
import org.specs2.mutable.Specification
import runner.JUnitRunner
import RestFormatters._
import model._
import org.junit.runner.RunWith
import net.liftweb.json.JsonAST
import java.io.StringWriter
import net.liftweb.common.{Failure, Full}
import bootstrap.liftweb.{BootTest, Boot}

@RunWith(classOf[JUnitRunner])
class RestFormattersSpec extends Specification {

  lazy val account = Database.createAccount
  lazy val expense = Database.createExpense
  step(BootTest.boot)

  "An Expense" should {
    "have a REST id" in {
      restId(expense) must_== "http://www.pocketchangeapp.com/api/expense/-1"
    }
    "have a timestamp" in {
      restTimestamp(expense) must_== "2011-01-01T00:00:00Z"
    }
    "be translatable as xml" in {
      toXML(expense) must ==/ {
        <expense>
          <id>http://www.pocketchangeapp.com/api/expense/-1</id>
          <date>2011-01-01T00:00:00Z</date>
          <description>expense</description>
          <accountname>Account1</accountname>
          <accountid>-1</accountid>
          <amount>100.0</amount>
          <tags></tags>
        </expense>
      }
    }
    "be translatable as json" in {
      val writer = new StringWriter
      JsonAST.render(toJSON(expense)).format(50, writer)

      writer.toString must_==
      """|{
         |  "expense":{
         |    "id":"http://www.pocketchangeapp.com/api/expense/-1",
         |    "date":"2011-01-01T00:00:00Z",
         |    "description":"expense",
         |    "accountname":"Account1",
         |    "accountid":-1,
         |    "amount":"100.0",
         |    "tags":""
         |  }
         |}""".stripMargin('|').replace("\r", "")
    }
    "be translatable to Atom" in {
      toAtom(expense).toString === {
    <entry>
      <id>urn:uuid:http://www.pocketchangeapp.com/api/expense/-1</id>
      <title>expense</title>
      <updated>2011-01-01T00:00:00Z</updated>
      <content type="xhtml">
        <div xmlns="http://www.w3.org/1999/xhtml">
          <table>
          <tr><th>Amount</th><th>Tags</th><th>Receipt</th></tr>
          <tr><td>100.0</td>
              <td></td>
              <td>None</td></tr>
          </table>
        </div>
      </content>
    </entry>.toString
      }
    }
  }
  "An Expense" can {
    "be created from a Map of values" >> {
      "this succeeds if all values can be parsed" in {
        fromMap(Map("date"        -> "2011-01-01T00:00:00Z",
                    "description" -> "expense",
                    "amount"      -> "100.0"), account).open_! must_== expense
      }
      "it returns a failure if" >> {
        "the date cannot be created from a Map of values" in {
          fromMap(Map("date"        -> "xxxx",
                      "description" -> "expense",
                      "amount"      -> "100.0"), account) must_== Failure("Failed to parse date")
        }
        "the amount cannot be created from a Map of values" in {
          fromMap(Map("date"        -> "2011-01-01T00:00:00Z",
                      "description" -> "expense",
                      "amount"      -> "xxxx"), account) must_== Failure("Failed to parse amount")
        }
        "a field is missing" in {
          fromMap(Map("description" -> "expense",
                      "amount"      -> "100.0"), account) must_== Failure("Invalid expense. Missing: date")
        }
      }
    }
    "be created from a json document" >> {
      val json = """ { "date": "2011-01-01T00:00:00Z",
                       "description": "expense",
                       "amount": "100.0" } """
      
      "this succeeds if all values can be parsed" in {
        fromJSON(Full(json.toArray.map(_.toByte)), account).open_! must_== expense
      }
    }
    "be created from an xml document" >> {
      val xml = <expense>
                  <date>2011-01-01T00:00:00Z</date>
                  <description>expense</description>
                  <amount>100.0</amount>
                </expense>

      "this succeeds if all values can be parsed" in {
        fromXML(Full(xml), account).open_! must_== expense
      }
    }
  }
}
package by.xotonic

import by.xotonic.collections.{CyclicList, Node}
import com.typesafe.scalalogging.StrictLogging
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class CyclicListTest extends FlatSpec with Matchers with StrictLogging {

  "A CyclicList" should "be empty when called with default constructor" in {
    val list: CyclicList[Int] = new CyclicList[Int]()
    list.isEmpty should be(true)
  }

  "Yah, Add and Get " should "work" in {
    val c = new CyclicList[String]
    c.add("1")
    c.add("2")
    c.add("3")
    c.add("4")
    c.add("5")
    c.add("6")
    c.add("7")
    c.add("8")

    logger.debug("List fulfilled")

    val list = new ListBuffer[Node[String]]

    c.foreach(data => {
      list += data
    })

    logger.debug(list.toString)

    c(0).get should be("1")
    c(1).get should be("2")
    c(2).get should be("3")
    c(3).get should be("4")
    c(4).get should be("5")
    c(5).get should be("6")
    c(6).get should be("7")
    c(7).get should be("8")
  }

  "The Get " should " return repeating nodes if index > size" in {
    val c = new CyclicList[String]
    c.add("first")
    c.add("second")
    c(2).get should be("first")
    c(3).get should be("second")
  }

  "Remove " should "work" in {

    val c = new CyclicList[String]
    c.add("0")
    c.add("1")
    c.add("2")
    c.add("3")

    c.remove(2)

    c(0).get should be("0")
    c(1).get should be("1")
//    c(2).get should be("2")
    c(2).get should be("3")

  }

  "Remove in 0" should "work like a nigga" in {

    val c = new CyclicList[String]
    c.add("0")
    c.add("1")
    c.add("2")
    c.add("3")

    // todo remove
    c.remove(0)

    c(0).get should be("1")
    c(1).get should be("2")
    c(2).get should be("3")

  }


}

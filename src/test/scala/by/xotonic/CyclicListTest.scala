package by.xotonic

import by.xotonic.collections.{CyclicList, Node}
import com.typesafe.scalalogging.StrictLogging
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class CyclicListTest extends FlatSpec with Matchers with StrictLogging {


  def print[T](c : CyclicList[T]) =
  {
    val list = new ListBuffer[Node[T]]

    c.foreach(data => {
      list += data
    })

    logger.debug(list.toString)
  }

  "A CyclicList" should "be empty when called with default constructor" in {
    val list: CyclicList[Int] = new CyclicList[Int]()
    list.isEmpty should be(true)
  }

  "Add and Get " should "work" in {
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

    print(c)

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
//   c(2).get should be("2")
    c(2).get should be("3")

  }

  "You " can "remove element in 0 position" in {

    val c = new CyclicList[String]
    c.add("0")
    c.add("1")
    c.add("2")
    c.add("3")

    c.remove(0)

    c(0).get should be("1")
    c(1).get should be("2")
    c(2).get should be("3")

  }

  "Sort " should "work" in
  {

    logger.debug("Start sort test")

    val c = new CyclicList[Int]
    c.add(1)
    c.add(4)
    c.add(3)
    c.add(5)
    c.add(2)
    c.add(6)

    val r = c.sort((x,y) => { x - y})

    logger.debug(r.toString)

    r(0).get should be (1)
    r(1).get should be (2)
    r(2).get should be (3)
    r(3).get should be (4)
    r(4).get should be (5)
    r(5).get should be (6)

  }
}

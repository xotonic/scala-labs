package by.xotonic

import by.xotonic.collections.CyclicList
import org.scalatest.{FlatSpec, Matchers}

class CyclicListTest extends FlatSpec with Matchers {

  "A CyclicList" should "be empty when called with default constructor" in {
    val list: CyclicList[Int] = new CyclicList[Int]()
    list.isEmpty should be (true)
  }

  "Yah, Add and Get " should "work" in
  {
    val c = new CyclicList[String]
    c.add("first")
    c.add("second")

    c.get(0).get should be ("first")
    c.get(1).get should be ("second")

    c.get(2).get should be ("first")
  }


}

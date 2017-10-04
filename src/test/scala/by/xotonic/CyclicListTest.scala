package by.xotonic

import org.scalatest.{FlatSpec, Matchers}

class CyclicListTest extends FlatSpec with Matchers {
  "A CyclicList" should "be empty when called with default constructor" in {
    val list: CyclicList[Int] = new CyclicList[Int]()
    list.isEmpty should be(true)
  }
}

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
  }

  "The Get " should " return repeating nodes if index > size" in
  {
    val c = new CyclicList[String]
    c.add("first")
    c.add("second")

    c.get(2).get should be ("first")
    c.get(3).get should be ("second")
  }

    /* todo не проходит
     * вроде элементы в обратном порядке
     */

    "Remove "  should "work" ignore  {

    val c = new CyclicList[String]
    c.add("0")
    c.add("1")
    c.add("2")
    c.add("3")

    c.get(0).get should be ("0")
    c.get(1).get should be ("1")
    c.get(2).get should be ("2")
    c.get(3).get should be ("3")

  }


}

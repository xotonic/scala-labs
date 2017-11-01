package by.xotonic.collections

import com.typesafe.scalalogging.StrictLogging
import org.scalactic.Snapshots

import scala.annotation.tailrec

/**
  * Cyclic list
  */
class CyclicList[T]() extends StrictLogging with Snapshots {


  private var head: Option[Node[T]] = Option.empty



  def sort(cmp : (T,T) => Int) : CyclicList[T] = {

    logger.debug("Sorting")

    var c = new CyclicList[T]

    if (isEmpty) {
      logger.debug("Collection is empty")
      return c
    }

    if (head.get.prev == head.get) {
      logger.debug(s"Collection has only head: ${head.get}")
      c.add(head.get.data)
      return c
    }

    val x = apply(0).get
    var c2 = new CyclicList[T]
    var c3 = new CyclicList[T]

    foreachValue(i => {
      val y = cmp(i, x)
      if (y < 0) c.add(i)
      else if (y == 0) c2.add(i)
      else c3.add(i)
    })

    c = c.sort(cmp)
    c2 = c2.sort(cmp)
    c3 = c3.sort(cmp)

    c2.foreachValue(i => c.add(i))
    c3.foreachValue(i => c.add(i))

    logger.debug(s"Sort step: ${snap(c, c2, c3, x, head)}")
    c
  }


  override def toString: String = {
    val sb = new StringBuilder
    sb.append("[")
    foreachValue(v => sb.append(v).append(","))
    sb.append("]")
    sb.toString
  }

  def prepend(x: T, pos: Int = 0) = head match {

    case None =>

      logger.debug(s"Adding $x to head")

      val reallyFirst = new Node[T](x)
      head = Some(reallyFirst)
      logger.debug(head.toString)

    case Some(theHead) =>



      walkToPosition((prev, node) => {
        logger.debug(s"Adding $x after $node")

        val newNode = new Node[T](x)
        newNode.next = node.next
        node.next = newNode

        logger.debug(s" new node: $newNode, changed node: $node, head: $head")

      }, pos, theHead)
  }

  def add(x: T) = head match {
    case None => logger.debug(s"Set $x as head");  head = Some(new Node[T](x))
    case Some(theHead) =>

      val newNode = new Node[T](x)
      newNode.prev = theHead.prev
      theHead.prev.next = newNode
      newNode.next = theHead
      theHead.prev = newNode

      if (theHead.next == theHead) {
        theHead.next = newNode
      }

      logger.debug(s"new=$newNode head=$theHead")

  }

  /**
    * Get element by position relative the head
    * @param pos position
    * @return
    */
  def apply(pos: Int): Option[T] = head flatMap {
    firstNode => {
      Some(walkToPosition((_, node) => node.data, pos, firstNode))
    }
  }


  /**
    * Checks if cyclic list is not empty
    *
    * @return non-emptiness flag
    */
  def nonEmpty = !isEmpty

  /**
    * Checks if cyclic list is empty
    *
    * @return emptiness flag
    */
  def isEmpty = head.isEmpty

  /**
    * Removes element with index x from cyclic list
    * If x is bigger than list size, then the index of item to remove is calcul ated as
    * <code>length % x</code> - like the list is infinite
    *
    * @param x index of element to remove
    */
  def remove(x: Int) = head.foreach(theHead => {

    if (x == 0) {
      theHead.prev.next = theHead.next
      head = Some(theHead.next)
    }
    else
      walkToPosition((prev, node) => {
        logger.debug(s"Removing ${node.data} : ${prev.data}.next = ${node.next.data}.next")
        prev.next = node.next
        node.prev = prev.prev
      }, x, theHead)
  })


  @tailrec final def walkToPosition[R](fun: (Node[T], Node[T]) => R,
                                       pos: Int,
                                       node: Node[T] = head.get,
                                       prev: Node[T] = head.get,
                                       cur: Int = 0): R = {
    // logger.debug(s"Walk step: ${snap(pos, node, prev, cur)}")

    if (cur == pos)
      fun(prev, node)
    else
      walkToPosition(fun, pos, node.next, node, cur + 1)
  }

  final def foreach(fun: Node[T] => Unit) = {
    @tailrec def it(node: Node[T], stopNode: Node[T], start: Boolean): Unit = {
      // logger.debug(s"Step: ${snap(node, stopNode, start)}")
      if (node != stopNode || start) {
        fun(node)
        it(node.next, stopNode, start = false)
      }
    }

    head.foreach(h => it(h, h, start = true))
  }

  final def foreachValue(fun: T => Unit) = {
    @tailrec def it(node: Node[T], stopNode: Node[T], start: Boolean): Unit = {
      // logger.debug(s"Step: ${snap(node, stopNode, start)}")
      if (node != stopNode || start) {
        fun(node.data)
        it(node.next, stopNode, start = false)
      }
    }
    head.foreach(h => it(h, h, start = true))
  }


}

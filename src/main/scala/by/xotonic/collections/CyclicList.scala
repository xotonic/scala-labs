package by.xotonic.collections

import com.typesafe.scalalogging.StrictLogging
import org.scalactic.Snapshots
import scala.annotation.tailrec

/**
  * Cyclic list
  */
class CyclicList[T <: Any]() extends StrictLogging with Snapshots {


  private var head: Option[Node[T]] = Option.empty


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
    case None => head = Some(new Node[T](x))
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
  def remove(x: Int) =  if (head.nonEmpty)

    walkToPosition((prev, node) => {
      logger.debug(s"Removing ${node.data} : ${prev.data}.next = ${node.next.data}.next")
      prev.next = node.next
      node.prev = prev.prev
    }, x, head.get)


  /**
    * Sorts cyclic list
    */
  def sort() = None


  @tailrec final def walkToPosition[R](fun : (Node[T], Node[T]) => R,
                                       pos: Int,
                                       node: Node[T] = head.get,
                                       prev: Node[T] = head.get,
                                       cur: Int = 0) : R =
  {
    logger.debug(s"Walk step: ${snap(pos, node, prev, cur)}")

    if (cur == pos)
      fun(prev, node)
    else
      walkToPosition(fun, pos, node.next, node, cur + 1)
  }

  final def foreach(fun: Node[T] => Unit) =
  {
    @tailrec def it(node: Node[T], stopNode: Node[T], start: Boolean) : Unit =
    {
      logger.debug(s"Step: ${snap(node, stopNode, start)}")
      if (node != stopNode || start) {
        fun(node)
        it(node.next, stopNode, start = false)
      }
    }

    head.foreach(h => it(h, h, start = true))
  }


}

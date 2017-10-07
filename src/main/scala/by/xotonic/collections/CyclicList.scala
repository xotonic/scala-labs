package by.xotonic.collections

import com.typesafe.scalalogging.StrictLogging

/**
  * Cyclic list
  */
class CyclicList[T <: Any]() extends StrictLogging {


  private var head: Option[Node[T]] = Option.empty


  def add(x: T, pos: Int = 0) = head match {

    case None =>

      logger.debug(s"Adding $x to head")

      val reallyFirst = new Node[T](x)
      head = Some(reallyFirst)

    case Some(firstNode) =>

      walkToPosition( (_, node) => {
        logger.debug(s"Adding $x before ${node.next.data}")

        val newNode = new Node[T](x)
        newNode.next = node.next
        node.next = newNode
      }, pos, firstNode)
  }

  /* todo удалить Option , тк индекс всегда ходит по кругу */

  def get(pos: Int): Option[T] = head flatMap(firstNode => {
    Some(walkToPosition((_, node) => node.data, pos, firstNode))
  })


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
    }, x, head.get)


  /**
    * Sorts cyclic list
    */
  def sort() = {

  }


  private def walkToPosition[R](fun : (Node[T], Node[T]) => R , pos: Int,
                        node: Node[T] = head.get, prev: Node[T] = head.get, cur: Int = 0
                       ) : R =
  {

    logger.debug(s"Walk step: node : ${node.data}, cur : $cur")

    if (cur == pos)
      fun(prev, node)
    else
      walkToPosition(fun, pos, node.next, node, cur + 1)
  }

}

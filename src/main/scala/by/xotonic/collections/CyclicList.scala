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

      def walkAndAdd(node: Node[T], cur: Int): Unit = {

        logger.debug(s"Walk step: node : ${node.data}, cur : $cur")

        if (cur == pos) {

          logger.debug(s"Adding $x before ${node.next.data}")

          val newNode = new Node[T](x)
          newNode.next = node.next
          node.next = newNode
        }
        else
          walkAndAdd(firstNode.next, cur + 1)
      }

      walkAndAdd(firstNode, 0)
  }

  def get(pos: Int): Option[T] = head.flatMap(firstNode => {

    def walk(node: Node[T], cur: Int): T = {
      if (cur == pos) node.data
      else walk(node.next, cur + 1)
    }

    Some(walk(firstNode, 0))
  })

  /*
    /**
      * Gets an element from cyclic list with specified index.
      *
      * @param pos index of element to get
      * @return Element with specified index
      */
    def get(pos: Int): T = {
     None
    }
  */

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
    * If x is bigger than list size, then the index of item to remove is calculated as
    * <code>length % x</code> - like the list is infinite
    *
    * @param x index of element to remove
    */
  def remove(x: Int): Unit = {

  }

  /**
    * Sorts cyclic list
    */
  def sort() = {

  }


  def walkToPosition[R](fun : Node[T] => R, pos: Int,
                        node: Node[T] = head.get, cur: Int = 0
                       ) : R =
  {
    if (cur == pos)
      fun(node)
    else
      walkToPosition(fun, pos, node, cur + 1)
  }

}

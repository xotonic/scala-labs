package by.xotonic

import scala.collection.mutable.ArrayBuffer

/**
  * Cyclic list for any reference type
  */
class CyclicList[T <: Any]() {

  private[CyclicList] class Node[N <: Any](val data: N, var next: Option[Node[N]])

  def this(first: T) = {
    this()
    array += new Node[T](first, None)
  }

  def this(elements: List[T]) = {
    this()
    elements.foreach(t => {
      array += new Node[T](t, None)
      if (array.length >= 2) {
        array(array.length - 2).next = Some(array(array.length - 1))
      }
    })
    array(array.length - 1).next = Some(array(0))
  }

  var array: ArrayBuffer[Node[T]] = ArrayBuffer.empty[Node[T]]

  /**
    * Adds element x of type T to the end of the cyclic list
    *
    * @param x element to add to the cyclic list
    */
  def add(x: T) = {
    array += new Node[T](x, None)
    if (array.length >= 2) {
      array(array.length - 2).next = Some(array(array.length - 1))
    }
  }

  /**
    * Gets an element from cyclic list with specified index.
    *
    * @param pos index of element to get
    * @return Element with specified index
    */
  def get(pos: Int): T = {
    if (pos > array.length)
      array(array.length % pos).data
    else
      array(pos).data
  }

  /**
    * Inserts element x of type T to the cyclic list with <code>pos</code> index, moving existing elements
    *
    * @param x   element to add to the cyclic list
    * @param pos position to insert element x
    */
  def insert(x: T, pos: Int) = {

  }

  /**
    * Checks if cyclic list is empty
    *
    * @return emptiness flag
    */
  def isEmpty: Boolean = return array.isEmpty

  /**
    * Checks if cyclic list is not empty
    *
    * @return non-emptiness flag
    */
  def nonEmpty: Boolean = return !isEmpty

  /**
    * Removes element with index x from cyclic list
    * If x is bigger than list size, then the index of item to remove is calculated as
    * <code>length % x</code> - like the list is infinite
    *
    * @param x index of element to remove
    */
  def remove(x: Int): Unit = {
    if (array isEmpty) return
    if (array.length == 1) {
      array.clear()
      return;
    }
    val index: Int = if (x > array.length) array.length % x else x
    if (index == 0) {
      array(array.length - 1).next = Some(array(1))
      array.remove(0)
      return
    }
    val nextNode: Option[Node[T]] = array(index).next
    array(index - 1).next = nextNode
    array.remove(index)
  }

  /**
    * Sorts cyclic list
    */
  def sort() = {

  }
}

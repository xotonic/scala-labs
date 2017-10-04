package by.xotonic

/**
  * Cyclic list for any reference type
  */
class CyclicList[T <: AnyRef]() {

  private class Node[N <: AnyRef](val data: N, var next: Node[N]) {

  }

  var list: List[Node[T]] = List()

  /**
    * Adds element x of type T to the end of the cyclic list
    *
    * @param x element to add to the cyclic list
    */
  def add(x: T) = {

  }

  /**
    * Gets an element from cyclic list with specified index.
    *
    * @param pos index of element to get
    * @return Option of type T with element with specified index or None
    */
  def get(pos: Int): Option[T] = {
    return None
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
    * Removes element with index x from cyclic list
    *
    * @param x index of element to remove
    */
  def remove(x: Int) = {

  }

  /**
    * Sorts cyclic list
    */
  def sort() = {

  }
}

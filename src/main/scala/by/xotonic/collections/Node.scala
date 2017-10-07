package by.xotonic.collections

/**
  * Created by xoton on 07.10.2017.
  */
private[collections] class Node[T <: Any](initData: T)
{
  var next : Node[T] = this
  val data = initData
}


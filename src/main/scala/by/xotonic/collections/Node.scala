package by.xotonic.collections

/**
  * Created by xoton on 07.10.2017.
  */
class Node[T](initData: T)
{
  var next : Node[T] = this
  var prev : Node[T] = this
  val data = initData

  override def toString = s"[$$${prev.data} => $data => $$${next.data}]"
}


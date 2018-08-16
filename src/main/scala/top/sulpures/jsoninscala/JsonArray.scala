package top.sulpures.jsoninscala

import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSONArray

/**
  * Created by shadow on 2018/8/14.
  */
class JsonArray {
  private var value = new ArrayBuffer[Any]()

  def this(array: Seq[Any]) {
    this()
    value = ArrayBuffer(array)
  }

  def add(item: Any): Unit ={
    value += item
  }

  def addAll(iter: Iterable[Any]): Unit ={
    value ++= iter
  }

  def remove(index: Int): Unit ={
    value.remove(index)
  }

  def clear(): Unit ={
    value.clear()
  }

  override def toString: String = {
    JSONArray(value.toList).toString()
  }

}

object JsonArray{
  def praser(str: String): JsonArray ={
    new Praser(str).toJsonArray()
  }
}

package top.sulpures.jsoninscala

import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSONArray

/**
  * Created by shadow on 2018/8/14.
  */
class JsonArray extends Iterable[Any]{
  private var value = new ArrayBuffer[Any]()

  def this(array: Seq[Any]) {
    this()
    value = ArrayBuffer(array)
  }

  def getInt(index: Int): Unit ={
    value(index).asInstanceOf[Int]
  }

  def getLong(index: Int): Long ={
    value(index).asInstanceOf[Long]
  }

  def getFloat(index: Int): Float ={
    value(index).asInstanceOf[Float]
  }

  def getDouble(index: Int): Double ={
    value(index).asInstanceOf[Double]
  }

  def getString(index: Int): String ={
    value(index).asInstanceOf[String]
  }

  def add(item: Any): JsonArray ={
    value += item
    this
  }

  def addAll(iter: Iterable[Any]): Unit ={
    value ++= iter
  }

  def remove(index: Int): Any ={
    value.remove(index)
  }

  def clear(): Unit ={
    value.clear()
  }

  def length(): Int = {
    value.length
  }

  override def toString: String = {
    JSONArray(value.toList).toString()
  }

  override def iterator: Iterator[Any] = {
    value.iterator
  }

  def contains(elem: Any): Boolean = {
    value.contains(elem)
  }

  def toArray(): Array[Any] ={
    value.toArray
  }
}

object JsonArray{
  def praser(str: String): JsonArray ={
    new Praser(str).toJsonArray()
  }
}

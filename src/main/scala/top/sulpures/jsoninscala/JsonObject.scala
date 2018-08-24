package top.sulpures.jsoninscala

import scala.util.parsing.json.JSONObject

/**
  * Created by shadow on 2018/8/14.
  */
class JsonObject extends Iterable[(String, Any)]{
  private val map = scala.collection.mutable.HashMap[String, Any]()

  def put(key: String, value: Any): Unit ={
    map.put(key, value)
  }

  def get(key: String): Any ={
    map.getOrElse(key, null)
  }

  def getInt(key: String): Int ={
    map.getOrElse(key, null).asInstanceOf[Int]
  }

  def getLong(key: String): Long ={
    map.getOrElse(key, null).asInstanceOf[Long]
  }

  def getFloat(key: String): Float ={
    map.getOrElse(key, null).asInstanceOf[Float]
  }

  def getDouble(key: String): Double ={
    map.getOrElse(key, null).asInstanceOf[Double]
  }

  def getString(key: String): String ={
    map.getOrElse(key, null).asInstanceOf[String]
  }

  def getJsonObject(key: String): JsonObject ={
    map.getOrElse(key, null).asInstanceOf[JsonObject]
  }

  def getJsonArray(key: String): JsonArray ={
    map.getOrElse(key, null).asInstanceOf[JsonArray]
  }

  def getOrElse[T >: Any](key: String, default: T): T ={
    map.getOrElse(key, default)
  }


  override def toString: String = {
    JSONObject(map.toMap).toString()
  }

  def keys(): Iterator[String] = {
    map.keysIterator
  }

  override def iterator: Iterator[(String, Any)] = {
    map.toIterator
  }
}

object JsonObject{
  def praser(jsonStr: String): JsonObject ={
    new Praser(jsonStr).toJsonObject()
  }
}
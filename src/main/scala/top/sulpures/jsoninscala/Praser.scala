package top.sulpures.jsoninscala

import scala.collection.mutable.ArrayBuffer

/**
  * Created by shadow on 2018/8/14.
  */
class Praser(jsonStr: String){

  val str = new JsonStrIterator(jsonStr)

  def toJsonObject(): JsonObject ={
    getJsonObject()
  }

  def toJsonArray(): JsonArray ={
    getJsonArray()
  }

  def nextShuoldBe(char: Char): Boolean ={
    if (getValidNext() != char){
      throw new Exception(s"错误的json格式，第${str.index + 1}个字符处")
    }
    true
  }

  def getValidNext(): Char ={
    str.next() match {
      case ' ' | '\r' | '\n' => getValidNext()
      case c: Char => c
    }
  }

  private[jsoninscala] def getKey(): String ={
    val key = new StringBuilder
    var flag = true
    var char: Char = 0

    while (flag){
      char = str.next()
      if ((char >= 48 && char <= 57) || (char >= 65 && char <= 90) || (char >= 97 && char <= 122) || char == 95){
        key += char
      }else{
        flag = false
      }
    }

    if (char != '\"'){
      throw new Exception(s"错误的json格式，第${str.index + 1}个字符处")
    }

    key.mkString
  }

  def getException(): Exception ={
    new Exception(s"错误的json格式，第${str.index + 1}个字符处")
  }


  private[jsoninscala] def getValue(): Any ={
    val char = getValidNext()
    str.goAhead(1)
    char match {
      case '{' =>
        getJsonObject()
      case '[' =>
        getJsonArray()
      case '\"' =>
        getString()
      case _ =>
        getBasic()
    }
  }

  private def getJsonObject(): JsonObject ={
    val jsonObject = new JsonObject
    nextShuoldBe('{')
    var char = getValidNext()
    if (char != '}'){
      str.goAhead(1)
    }

    while (char != '}'){
      nextShuoldBe('\"')
      val key = getKey()
      nextShuoldBe(':')
      val value = getValue()
      jsonObject.put(key, value)

      char = getValidNext()
      if (char != '}' && char != ','){
        throw new Exception(s"错误的json格式，第${str.index + 1}个字符处")
      }
    }

    jsonObject
  }

  private def getJsonArray(): JsonArray ={
    val sb = new StringBuilder
    val array = new ArrayBuffer[Any]
    val jsonArray = new JsonArray()
    nextShuoldBe('[')
    var char = str.next()
    str.goAhead(1)
    while (char != ']'){
      val value = getValue()
      jsonArray.add(value)
      char = getValidNext()
      if (char != ']' && char != ','){
        throw new Exception(s"错误的json格式，第${str.index + 1}个字符处")
      }
    }
    jsonArray
  }

  def getDigit(): Any = {
    val sb = new StringBuilder
    var char = str.next()
    var flag = true
    var isDouble = false
    if ((char >= 48 && char <= 57) || char == '-'){
      sb += char
      while (flag){
        char = str.next()
        if ((char >= 48 && char <= 57) || char == '.' || char == 'e' || char == 'E' || char == '-' || char == '+'){
          sb += char
          if (char == '.'){
            isDouble = true
          }
        } else {
          str.goAhead(1)
          flag = false
        }
      }
    }
    numberOf(sb.mkString)
  }

  def numberOf(str: String): Any ={
    var flag = true
    var index = 0
    var value: Any = null
    while (flag && index < 3){
      try {
        index match {
          case 0 => value = str.toInt
          case 1 => value = str.toLong
          case 2 => value = str.toDouble
        }
        flag = false
      } catch {
        case e: Exception => index += 1
      }
    }
    if (flag){
      throw getException()
    }
    value
  }

  private def getBasic(): Any ={
    val char = str.next()
    str.goAhead(1)
    char match {
      case 't' => if (getNextString(4) == "true") return true else throw getException()
      case 'f' => if (getNextString(5) == "false") return false else throw getException()
      case 'n' => if (getNextString(4) == "null") return null else throw getException()
      case _ => getDigit()
    }
  }

  private def getNextString(length: Int): String = {
    val sb = new StringBuilder
    for (i <- 0.until(length)){
      sb += str.next()
    }
    sb.mkString
  }

  private def getString(): String ={
    nextShuoldBe('\"')
    val sb = new StringBuilder
    while (true) {
      var c = str.next
      c match {
        case '\u0000' | '\n' | '\r' =>
          throw getException()
        case '\\' =>
          c = str.next
          c match {
            case 'b' =>
              sb.append('\b')
            case 'f' =>
              sb.append('\f')
            case 'n' =>
              sb.append('\n')
            case 'r' =>
              sb.append('\r')
            case 't' =>
              sb.append('\t')
//            case 'u' =>
//              sb.append(Integer.parseInt(str.next(4.toInt), 16).toChar)
//            case 'x' =>
//              sb.append(Integer.parseInt(str.next(2.toInt), 16).toChar)
            case _ =>
              sb.append(c)
          }
        case _ =>
          if (c == '\"') return sb.toString
          sb.append(c)
      }
    }
    ""
  }

}


class JsonStrIterator(jsonStr: String) extends Iterator[Char] {

  val value = jsonStr.toCharArray
  private[jsoninscala] var index = -1
  private val maxLength = value.length


  override def hasNext: Boolean = {
    index < maxLength
  }

  override def next(): Char = {
    index += 1
    value(index)
  }


  def goAhead(step: Int) = {
    index -= step
  }
}

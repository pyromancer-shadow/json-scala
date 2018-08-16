# json-scala
json for scala


How to use:

    val jsonStr = "{\"name\":\"sulpures\"}"
    val json = JsonObject.praser(jsonStr)
    println(json.getString("name"))

    val json2 = new JsonObject()
    json2.put("name", "sulpures")
    println(json2.toString)

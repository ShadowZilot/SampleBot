package helpers

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun JSONObject.safetyString(key: String): String = try {
    getString(key)
} catch (e : JSONException) {
    ""
}

fun JSONObject.safetyInt(key: String) = try {
    getInt(key)
} catch (e: JSONException) {
    0
}

fun JSONObject.safetyLong(key: String) = try {
      getLong(key)
} catch (e: JSONException) {
    0L
}

fun JSONObject.safetyArray(key: String) : JSONArray = try {
    getJSONArray(key)
} catch (e: JSONException) {
    JSONArray()
}

fun JSONObject.safetyBoolean(key: String) = try {
    getBoolean(key)
} catch (e: JSONException) {
    false
}

fun JSONObject.safetyObject(key: String): JSONObject = try {
    getJSONObject(key)
} catch (e: JSONException) {
    JSONObject()
}
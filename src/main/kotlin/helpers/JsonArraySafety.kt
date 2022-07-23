import org.json.JSONArray
import org.json.JSONException

fun JSONArray.safetyString(index: Int): String {
    return try {
        getString(index)
    } catch (e : JSONException) {
        ""
    }
}
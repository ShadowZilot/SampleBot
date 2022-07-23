package helpers


import org.json.JSONArray

fun <T: Any> JSONArray.forEach(action: (T) -> Unit) {
    for (i in 0 until length()) {
        action(get(i) as T)
    }
}


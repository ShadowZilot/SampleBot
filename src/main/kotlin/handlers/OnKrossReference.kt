package handlers

import org.json.JSONException
import org.json.JSONObject

class OnKrossReference : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            val text = updating.getJSONObject("message").getString("text")
            if (text.contains("https://www.instagram.com/p/")) {
                updating
            } else {
                throw UnhandledEvent()
            }
        } catch (e : JSONException) {
            throw UnhandledEvent()
        }
    }
}
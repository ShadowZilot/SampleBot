package handlers

import org.json.JSONException
import org.json.JSONObject

class OnMessageForwarded : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            updating
                .getJSONObject("message")
                .getJSONObject("forward_from_chat")
            updating
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}
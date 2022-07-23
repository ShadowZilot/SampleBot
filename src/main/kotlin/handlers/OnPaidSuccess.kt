package handlers

import org.json.JSONException
import org.json.JSONObject

class OnPaidSuccess : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            if (updating
                    .getJSONObject("message")
                    .has("successful_payment")) {
                updating
            } else {
                throw UnhandledEvent()
            }
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}
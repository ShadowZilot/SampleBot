package handlers

import org.json.JSONException
import org.json.JSONObject

class OnPreCheckoutQuery : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {

        return try {
            if (updating.has("pre_checkout_query")) {
                updating
            } else {
                throw UnhandledEvent()
            }
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}
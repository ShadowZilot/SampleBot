package handlers

import org.json.JSONException
import org.json.JSONObject

class OnMediaGroupGotten : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            if (updating.getJSONObject("message")
                    .has("photo")
            ) {
                updating
            } else {
                throw UnhandledEvent()
            }
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}
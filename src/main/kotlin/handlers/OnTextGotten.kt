package handlers

import org.json.JSONException
import org.json.JSONObject

class OnTextGotten(
    private val mWaitMessage: String = ""
) : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            if (
                updating.getJSONObject("message")
                    .getString("text") == mWaitMessage || mWaitMessage.isEmpty()
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
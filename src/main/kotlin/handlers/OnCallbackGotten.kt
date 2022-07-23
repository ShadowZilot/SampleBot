package handlers

import org.json.JSONException
import org.json.JSONObject

class OnCallbackGotten(
    private val mAwaitingData: String = ""
) : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            if (updating.has("callback_query")
                && updating.getJSONObject("callback_query").has("data")
            ) {
                val data = updating.getJSONObject("callback_query")
                    .getString("data")
                if ((mAwaitingData.isNotEmpty() && mAwaitingData == data) || mAwaitingData.isEmpty()) {
                    updating
                } else {
                    throw UnhandledEvent()
                }
            } else {
                throw UnhandledEvent()
            }
        } catch (e: JSONException) {
            throw UnhandledEvent()
        }
    }
}
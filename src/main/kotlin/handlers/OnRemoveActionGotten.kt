package handlers

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class OnRemoveActionGotten(
    private val mBase: BotRecognizerEvent
) : BotRecognizerEvent {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            Updating(updating).map(mBase)
            if (updating.getJSONObject("callback_query")
                    .getString("data").contains("/remove")
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
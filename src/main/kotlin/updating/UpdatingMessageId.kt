package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingMessageId : Updating.Mapper<Long> {

    override fun map(updating: JSONObject): Long {
        return try {
            if (updating.has("callback_query")) {
                updating
                    .getJSONObject("callback_query")
                    .getJSONObject("message")
                    .getLong("message_id")
            } else if (updating.has("message")) {
                updating
                    .getJSONObject("message")
                    .getLong("message_id")
            } else {
                updating.getLong("message_id")
            }
        } catch (e: JSONException) {
            0L
        }
    }
}
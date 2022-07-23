package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingForwarderMessageId : Updating.Mapper<Long> {

    override fun map(updating: JSONObject): Long {
        return try {
            updating
                .getJSONObject("message")
                .getLong("forward_from_message_id")
        } catch (e: JSONException) {
            -1L
        }
    }
}
package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UserIdUpdating : Updating.Mapper<Long> {

    override fun map(updating: JSONObject): Long {
        return try {
            if (updating.has("callback_query")) {
                updating.getJSONObject("callback_query")
                    .getJSONObject("message")
                    .getJSONObject("chat")
                    .getLong("id")
            } else if (updating.has("pre_checkout_query")) {
                updating
                    .getJSONObject("pre_checkout_query")
                    .getJSONObject("from")
                    .getLong("id")
            } else {
                updating
                    .getJSONObject("message")
                    .getJSONObject("chat")
                    .getLong("id")
            }
        } catch (e : JSONException) {
            0L
        }
    }
}
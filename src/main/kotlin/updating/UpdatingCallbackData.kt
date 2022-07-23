package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingCallbackData : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating
                .getJSONObject("callback_query")
                .getString("data")
        } catch (e: JSONException) {
            ""
        }
    }
}
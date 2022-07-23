package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingMessage : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating.getJSONObject("message")
                .getString("text")
        } catch (e: JSONException) {
            ""
        }
    }
}
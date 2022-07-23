package updating

import core.Updating
import org.json.JSONObject

class IsFromGroup : Updating.Mapper<Boolean> {

    override fun map(updating: JSONObject): Boolean {
        return if (updating.has("callback_query")) {
            updating.getJSONObject("callback_query")
                .getJSONObject("message")
                .getJSONObject("chat")
                .getString("type") == "group"
        } else if (updating.has("pre_checkout_query")) {
            true
        } else {
            updating
                .getJSONObject("message")
                .getJSONObject("chat")
                .getString("type") == "group" || updating
                .getJSONObject("message")
                .getJSONObject("chat")
                .getString("type") == "subgroup"
        }
    }
}
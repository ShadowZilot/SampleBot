package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingChatId : Updating.Mapper<Pair<String, Long>> {

    override fun map(updating: JSONObject): Pair<String, Long> {
        return try {
            if (updating.has("callback_query")) {
                Pair(
                    updating
                        .getJSONObject("callback_query")
                        .getJSONObject("message")
                        .getJSONObject("chat")
                        .getString("type"),
                    updating
                        .getJSONObject("callback_query")
                        .getJSONObject("message")
                        .getJSONObject("chat")
                        .getLong("id")
                )
            } else {
                Pair(
                    updating.getJSONObject("message")
                        .getJSONObject("chat")
                        .getString("type"),
                    updating.getJSONObject("message")
                        .getJSONObject("chat")
                        .getLong("id")
                )
            }
        } catch (e: JSONException) {
            Pair("unknown", 0L)
        }
    }
}
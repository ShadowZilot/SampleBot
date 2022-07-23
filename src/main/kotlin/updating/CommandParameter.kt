package updating

import core.Updating
import helpers.safetyInt
import helpers.safetyString
import org.json.JSONException
import org.json.JSONObject

class CommandParameter : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating.getJSONObject("message")
                .safetyString("text").replace(" ", "").substring(
                    updating.getJSONObject("message").getJSONArray("entities").getJSONObject(0)
                        .safetyInt("length"),
                    updating.getJSONObject("message")
                        .safetyString("text").replace(" ", "").length
                )
        } catch (e: JSONException) {
            ""
        }
    }
}
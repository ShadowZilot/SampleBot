package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class VideoIdFromUpdating : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating.getJSONObject("message")
                .getJSONObject("video")
                .getString("file_id")
        } catch (e: JSONException) {
            ""
        }
    }
}
package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingMedia : Updating.Mapper<JSONObject> {

    override fun map(updating: JSONObject): JSONObject {
        return try {
            updating.put("isKrossPhotos", true)
            val message = updating
                .getJSONObject("message")
            return if (message.has("photo")) {
                JSONObject().apply {
                    put("type", "photo")
                    put(
                        "id", message.getJSONArray("photo").getJSONObject(
                            message.getJSONArray("photo").length() - 1
                        ).getString("file_id")
                    )
                }
            } else if (message.has("video")) {
                JSONObject().apply {
                    put("type", "video")
                    put(
                        "id", message
                            .getJSONObject("video")
                            .getString("file_id")
                    )
                }
            } else {
                JSONObject()
            }
        } catch (e: JSONException) {
            JSONObject()
        }
    }
}
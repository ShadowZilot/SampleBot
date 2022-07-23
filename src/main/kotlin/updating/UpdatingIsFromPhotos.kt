package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingIsFromPhotos(
    private val mOtherUpdating: Updating
) : Updating.Mapper<Boolean> {

    override fun map(updating: JSONObject): Boolean {
        val outerUpdating = updating
        return mOtherUpdating.map(object : Updating.Mapper<Boolean> {
            override fun map(updating: JSONObject): Boolean {
                return try {
                    updating
                        .getJSONObject("message")
                        .getJSONObject("chat")
                        .getLong("id") == outerUpdating
                        .getJSONObject("message")
                        .getJSONObject("chat")
                        .getLong("id") &&
                            (updating.getJSONObject("message").has("photo") || updating
                                .getJSONObject("message")
                                .has("video"))
                            && (outerUpdating.getJSONObject("message")
                        .has("photo") || outerUpdating.getJSONObject("message").has("video"))
                            && updating
                        .getJSONObject("message")
                        .getInt("message_id") - outerUpdating
                        .getJSONObject("message")
                        .getInt("message_id") == 1
                } catch (e: JSONException) {
                    false
                }
            }
        })
    }
}
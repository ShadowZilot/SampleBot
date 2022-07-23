package core.interceptor

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class IsUpdatingFromMediaGroup(
    private val mOtherUpdating: Updating
) : Updating.Mapper<Boolean> {

    override fun map(updating: JSONObject): Boolean {
        val outer = updating
        return mOtherUpdating.map(object : Updating.Mapper<Boolean> {
            override fun map(updating: JSONObject) = try {
                (outer.getJSONObject("message").has("forward_from_message_id")
                        && updating.getJSONObject("message").has("forward_from_message_id")
                        && outer.getJSONObject("message").getLong("media_group_id") == updating
                    .getJSONObject("message")
                    .getLong("media_group_id"))
            } catch (e: JSONException) {
                false
            }
        })
    }
}
package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class UpdatingPreCheckoutId : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            return updating
                .getJSONObject("pre_checkout_query")
                .getString("id")
        } catch (e: JSONException) {
            ""
        }
    }
}
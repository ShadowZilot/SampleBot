package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class ProviderTokenByUpdating : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating
                .getJSONObject("message")
                .getJSONObject("successful_payment")
                .getString("provider_payment_charge_id")
        } catch (e: JSONException) {
            ""
        }
    }
}
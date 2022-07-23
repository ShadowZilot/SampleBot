package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject

class InvoicePayloadByUpdating : Updating.Mapper<String> {

    override fun map(updating: JSONObject): String {
        return try {
            updating
                .getJSONObject("message")
                .getJSONObject("successful_payment")
                .getString("invoice_payload")
        } catch (e: JSONException) {
            ""
        }
    }
}
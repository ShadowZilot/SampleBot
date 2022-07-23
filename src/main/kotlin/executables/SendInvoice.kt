package executables

import Storages.stConfig
import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import updating.UpdatingChatId
import java.io.IOException

class SendInvoice(
    private val mKey: String,
    private val mOrderId: String,
    private val mDescription: String,
    private val mPrices: List<JSONObject>,
    private val mProviderData: JSONObject,
    private val mChatId: Long = -1,
    private val mMessageIdCallback: (id: Long) -> Unit = {}
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        val body = response.body!!.string()
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(body)
        } else {
            mMessageIdCallback.invoke(
                JSONObject(body)
                    .getJSONObject("result")
                    .getLong("message_id")
            )
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject): Request {
        return Request.Builder()
            .url("https://api.telegram.org/bot$mKey/sendInvoice")
            .post(
                FormBody.Builder()
                    .add(
                        "chat_id",
                        if (mChatId == -1L) {
                            Updating(updating).map(UpdatingChatId()).second.toString()
                        } else {
                            mChatId.toString()
                        }
                    )
                    .add(
                        "title", "Оплата заказа № $mOrderId"
                    )
                    .add("description", mDescription)
                    .add("payload", mOrderId)
                    .add("provider_data", mProviderData.toString(2  ))
                    .add("currency", "RUB")
                    .add("provider_token", stConfig.configValueString("paymentToken"))
                    .add("prices", JSONArray().apply {
                        mPrices.forEach {
                            put(it)
                        }
                    }.toString(2))
                    .build()
            )
            .build()
    }
}
package executables

import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingPreCheckoutId
import java.io.IOException

class AnswerToInvoiceCallback(
    private val mKey: String
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        val bodyString = response.body!!.string()
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(bodyString)
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject): Request {
        return Request.Builder()
            .post(
                FormBody.Builder()
                    .add(
                        "pre_checkout_query_id",
                        Updating(updating).map(UpdatingPreCheckoutId())
                    )
                    .add("ok", "true")
                    .build()
            )
            .url("https://api.telegram.org/bot$mKey/answerPreCheckoutQuery")
            .build()
    }
}
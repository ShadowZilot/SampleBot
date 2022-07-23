package executables

import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingCallbackQueryId
import java.io.IOException

class AnswerToCallback(
    private val mKey: String,
    private val mMessage: String
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject): Request {
        return Request.Builder()
            .post(
                FormBody.Builder()
                    .add(
                        "callback_query_id",
                        Updating(updating).map(
                            UpdatingCallbackQueryId()
                        )
                    )
                    .add("text", mMessage)
                    .add("show_alert", "false")
                    .build()
            )
            .url("https://api.telegram.org/bot${mKey}/answerCallbackQuery")
            .build()
    }
}
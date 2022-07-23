package executables

import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingMessageId
import updating.UserIdUpdating
import java.io.IOException

class DeleteMessage(
    private val mKey: String,
    private val mChatId: String = "",
    private val mMessageId: Long,
) : Executable {

    constructor(key: String, updating: Updating): this(
        key,
        updating.map(UserIdUpdating()).toString(),
        updating.map(UpdatingMessageId())
    )

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject) = Request.Builder()
        .post(FormBody.Builder()
            .add("chat_id", mChatId)
            .add("message_id", mMessageId.toString())
            .build())
        .url("https://api.telegram.org/bot$mKey/deleteMessage")
        .build()
}
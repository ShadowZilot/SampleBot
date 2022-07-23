package executables

import core.Updating
import keyboard_markup.KeyboardMarkup
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingMessageId
import updating.UserIdUpdating
import java.io.IOException

class EditTextMessage(
    private val mKey: String,
    private val mMessage: String,
    private val mEditingMessageId: Long = -1L,
    private val mMarkup: KeyboardMarkup = KeyboardMarkup.Dummy()
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(
                response.body!!.string()
            )
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject) = Request.Builder()
        .post(
            FormBody.Builder()
                .add(
                    "chat_id", Updating(updating).map(UserIdUpdating()).toString()
                )
                .add(
                    "message_id", if (mEditingMessageId == -1L) {
                        Updating(updating).map(UpdatingMessageId()).toString()
                    } else {
                        mEditingMessageId.toString()
                    }
                )
                .add("parse_mode", "MarkdownV2")
                .add("text", mMessage)
                .add("reply_markup", mMarkup.filed().toString(2))
                .build()
        )
        .url("https://api.telegram.org/bot${mKey}/editMessageText")
        .build()
}
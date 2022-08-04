package executables

import core.Updating
import keyboard_markup.KeyboardMarkup
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import updating.UserIdUpdating
import java.io.IOException

class SendMessage(
    private val mKey: String,
    private val mMessage: String,
    private val mMarkup: KeyboardMarkup = KeyboardMarkup.Dummy(),
    private val mDisableLinkPreview: Boolean = false,
    private val mChatId: Long = -1L,
    private val mFormatting: JSONArray = JSONArray(),
    private val mOnMessageIdGotten: (messageId: Int) -> Unit = {}
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
            if (response.code == 403) {
                Logging.ConsoleLog.log("Problem with user = $mChatId")
            }
        } else {
            val body = JSONObject(response.body!!.string())
                .getJSONObject("result")
            mOnMessageIdGotten.invoke(body.getInt("message_id"))
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject): Request {
        val body = FormBody.Builder()
            .add(
                "chat_id", if (mChatId == -1L) {
                    Updating(updating).map(UserIdUpdating()).toString()
                } else {
                    mChatId.toString()
                }
            )
            .add("text", mMessage)
            .add("disable_web_page_preview", mDisableLinkPreview.toString())
            .add("reply_markup", mMarkup.filed().toString(2))
        if (mFormatting.isEmpty) {
            body.add("parse_mode", "MarkdownV2")
        } else {
            body.add("entities", mFormatting.toString(2))
        }
        return Request.Builder()
            .post(
                body.build()
            )
            .url("https://api.telegram.org/bot${mKey}/sendMessage")
            .build()
    }
}
package executables

import core.Updating
import keyboard_markup.KeyboardMarkup
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingChatId
import java.io.IOException

class SendVideo(
    private val mKey: String,
    private val mVideoId: String,
    private val mCaption: String,
    private val mChatId: Long = -1L,
    private val mReplyMessageId: Long = -1L,
    private val mKeyboardMarkup: KeyboardMarkup = KeyboardMarkup.Dummy()
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
                FormBody.Builder().apply {
                    val chat = if (mChatId != -1L) {
                        mChatId
                    } else {
                        Updating(updating).map(
                            UpdatingChatId()
                        ).second.toString()
                    }
                    add("chat_id", chat.toString())
                    add("video", mVideoId)
                    add("caption", mCaption)
                    add("parse_mode", "MarkdownV2")
                    if (mReplyMessageId != -1L) {
                        add("reply_to_message_id", mReplyMessageId.toString())
                    }
                    add(
                        "reply_markup", mKeyboardMarkup
                            .filed()
                            .toString(2)
                    )
                }.build()
            )
            .url("https://api.telegram.org/bot${mKey}/sendVideo")
            .build()
    }
}
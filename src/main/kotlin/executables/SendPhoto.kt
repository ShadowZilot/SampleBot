package executables

import core.Updating
import keyboard_markup.KeyboardMarkup
import logs.Logging
import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import updating.UserIdUpdating
import java.io.IOException

class SendPhoto(
    private val mKey: String,
    private val mCaption: String,
    private val mImageId: String,
    private val mReplyMarkup: KeyboardMarkup = KeyboardMarkup.Dummy(),
    private val mListener: () -> Unit = {},
    private val mChatId : Long = -1L
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
        } else {
            mListener.invoke()
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject) = Request.Builder()
        .post(
            MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "chat_id",
                    if (mChatId == -1L) {
                        Updating(updating).map(UserIdUpdating()).toString()
                    } else {
                        mChatId.toString()
                    }
                )
                .addFormDataPart("parse_mode", "MarkdownV2")
                .addFormDataPart("caption", mCaption)
                .addFormDataPart("photo", mImageId)
                .addFormDataPart(
                    "reply_markup",
                    mReplyMarkup.filed().toString(2)
                )
                .build()
        )
        .url("https://api.telegram.org/bot${mKey}/sendPhoto")
        .build()
}
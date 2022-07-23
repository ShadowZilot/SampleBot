package executables

import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import updating.UserIdUpdating
import java.io.IOException

class SendMediaGroup(
    private val mKey: String,
    private val mImages: List<String>,
    private val mCaption: String
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
                        "chat_id",
                        Updating(updating).map(
                            UserIdUpdating()
                        ).toString()
                    )
                    .add("media", JSONArray().apply {
                        for (i in mImages.indices) {
                            val item = JSONObject()
                            item.put("type", "photo")
                            item.put("media", mImages[i])
                            if (i == 0) {
                                item.put("caption", mCaption)
                                item.put("parse_mode", "MarkdownV2")
                            }
                            put(item)
                        }
                    }.toString(2))
                    .build()
            )
            .url("https://api.telegram.org/bot${mKey}/sendMediaGroup")
            .build()
    }
}
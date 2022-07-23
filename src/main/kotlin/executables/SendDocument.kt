package executables

import core.Updating
import logs.Logging
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import updating.UpdatingChatId
import java.io.File
import java.io.IOException

class SendDocument(
    private val mKey: String,
    private val mFile: File
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
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "chat_id", Updating(updating).map(
                            UpdatingChatId()
                        ).second.toString()
                    )
                    .addFormDataPart(
                        "document", mFile.name, mFile.readBytes().toRequestBody(
                            "text/csv".toMediaTypeOrNull()
                        )
                    )
                    .build()
            )
            .url("https://api.telegram.org/bot${mKey}/sendDocument")
            .build()
    }
}
package executables

import logs.Logging
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class RequestExecutable(
    private val mRequest: Request
) : Executable {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
        }
        response.body?.close()
    }

    override fun map(updating: JSONObject) = mRequest
}
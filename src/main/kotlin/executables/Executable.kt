package executables

import core.Updating
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

interface Executable: Callback, Updating.Mapper<Request> {

    class Dummy : Executable {
        override fun onFailure(call: Call, e: IOException) {}

        override fun onResponse(call: Call, response: Response) {}

        override fun map(updating: JSONObject): Request {
            return Request.Builder()
                .url("")
                .build()
        }
    }
}
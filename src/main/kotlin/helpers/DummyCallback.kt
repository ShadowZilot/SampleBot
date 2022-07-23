package helpers

import logs.Logging
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class DummyCallback : Callback {

    override fun onFailure(call: Call, e: IOException) {}

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            Logging.ConsoleLog.log(response.body!!.string())
        }
        response.body?.close()
    }
}
package core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import logs.Logging.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class PollingHandling(
    private val mBot: Bot,
    private val mBotKey: String
) : BotHandling, Callback {
    private val mClient = OkHttpClient()
    private var mCurrentCall: CallHolding = CallHolding.Base(this)
    private var mMaxId = 1L

    override fun start() {
        mCurrentCall.updateCall(makeRequest())
        mBot.start()
    }

    override fun stop() {
        mCurrentCall.cancel()
        mBot.stop()
    }

    override fun onFailure(call: Call, e: IOException) {
        ConsoleLog.log("Error polling!")
        mCurrentCall.updateCall(makeRequest())
        ConsoleLog.log("Bot relaunched!")
    }

    override fun onResponse(call: Call, response: Response) {
        val body = response.body!!
        val string = body.string()
        val updating = UpdatingByResponse.Base(
            string
        )
        try {
            runBlocking(Dispatchers.IO) {
                mBot.fetchUpdates(updating.updates(), updating.updatingId())
            }
        } catch (e: Exception) {
            ConsoleLog.log(e.stackTraceToString())
        } finally {
            mMaxId = updating.updatingId() + 1
            mCurrentCall.updateCall(makeRequest())
        }
    }

    private fun makeRequest() = mClient.newCall(
        Request.Builder()
            .post(
                FormBody.Builder()
                    .add("limit", "15")
                    .add("timeout", "5")
                    .add(
                        "offset",
                        mMaxId.toString()
                    )
                    .build()
            )
            .url("https://api.telegram.org/bot$mBotKey/getUpdates")
            .build()
    )
}
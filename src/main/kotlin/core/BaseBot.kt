package core

import core.interceptor.UpdatesInterceptor
import logs.Logging
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class BaseBot(
    private val mClient: OkHttpClient,
    private val mInterceptor: UpdatesInterceptor
) : Bot {
    private val mListener = mutableListOf<UpdatesGotten>()

    override fun implementRequest(request: Request, listener: Callback) {
        mClient.newCall(request).enqueue(listener)
    }

    override fun start() {
        Logging.ConsoleLog.log("Staff bot started")
    }

    override fun stop() {
        Logging.ConsoleLog.log("Staff bot stopped!")
    }

    override suspend fun fetchUpdates(updates: List<Updating>, id: Long) {
        val handledUpdates = mInterceptor.updates(updates)
        mListener.forEach {
            it.fetchUpdates(handledUpdates, id)
        }
    }

    override fun addListener(listener: UpdatesGotten) {
        mListener.add(listener)
    }

    override fun clearListener() {
        mListener.clear()
    }
}
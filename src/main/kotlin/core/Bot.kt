package core

import okhttp3.Callback
import okhttp3.Request

interface Bot : BotControl, UpdatesGotten, ObserverBot {
    fun implementRequest(request: Request, listener: Callback)
}
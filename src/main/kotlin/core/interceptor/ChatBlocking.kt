package core.interceptor

import core.Updating
import updating.UpdatingChatId

class ChatBlocking(
    private val mInterceptor: UpdatesInterceptor,
    private val mAllowedId: Long
) : UpdatesInterceptor {

    override fun updates(inputUpdates: List<Updating>): List<Updating> {
        val result = inputUpdates.toMutableList()
        for (i in result.indices) {
            val chat = result[i].map(UpdatingChatId())
            if (chat.first == "group" && chat.second != mAllowedId) {
                result.remove(result[i])
            }
        }
        return mInterceptor.updates(result)
    }
}
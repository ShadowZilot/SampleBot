package core.interceptor

import core.Updating

class MediaInterceptor(
    private val mInterceptor: UpdatesInterceptor = UpdatesInterceptor.Dummy
) : UpdatesInterceptor {

    override fun updates(inputUpdates: List<Updating>): List<Updating> {
        val result = inputUpdates.toMutableList()
        return if (inputUpdates.size >= 2) {
            var comparing = IsUpdatingFromMediaGroup(inputUpdates.first())
            for (i in 1 until inputUpdates.size) {
                if (inputUpdates[i].map(comparing)) {
                    result.remove(inputUpdates[i])
                } else {
                    comparing = IsUpdatingFromMediaGroup(inputUpdates[i])
                }
            }
            mInterceptor.updates(result)
        } else {
            mInterceptor.updates(inputUpdates)
        }
    }
}
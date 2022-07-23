package core.interceptor

import core.Updating

interface UpdatesInterceptor {

    fun updates(inputUpdates: List<Updating>): List<Updating>

    object Dummy  : UpdatesInterceptor {
        override fun updates(inputUpdates: List<Updating>): List<Updating> {
            return inputUpdates
        }
    }
}
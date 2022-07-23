package core

interface ObserverBot {
    fun addListener(listener: UpdatesGotten)

    fun clearListener()
}
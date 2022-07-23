package core

interface UpdatesGotten {
    suspend fun fetchUpdates(updates: List<Updating>, id: Long = 1)
}
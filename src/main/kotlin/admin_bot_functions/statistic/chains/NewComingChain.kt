package admin_bot_functions.statistic.chains

import Storages
import chain.Chain
import core.Updating
import executables.Executable
import handlers.CommandEvent
import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType

class NewComingChain(
    private val mStatistic: StatisticHandling
) : Chain(CommandEvent("/start")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        try {
            Storages.stUsersStorage.user(updating)
        } catch (e: Exception) {
            Storages.stUsersStorage.rewrittenUser(updating)
            mStatistic.writeStatistic(
                updating,
                StatisticType.NewComing
            )
        }
        return listOf(Executable.Dummy())
    }
}
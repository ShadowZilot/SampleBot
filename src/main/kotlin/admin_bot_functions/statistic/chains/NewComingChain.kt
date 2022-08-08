package admin_bot_functions.statistic.chains

import Storages
import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType
import chain.Chain
import core.Updating
import executables.Executable
import handlers.CommandEvent
import updating.CommandParameter

class NewComingChain(
    private val mStatistic: StatisticHandling
) : Chain(CommandEvent("/start")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        try {
            Storages.stUsersStorage.user(updating)
        } catch (e: Exception) {
            Storages.stUsersStorage.rewrittenUser(updating)
            try {
                Storages.stDeeplink.plusUserToLink(
                    updating.map(CommandParameter())
                )
            } finally {
                mStatistic.writeStatistic(
                    updating,
                    StatisticType.NewComing
                )
            }
        }
        return listOf(Executable.Dummy())
    }
}
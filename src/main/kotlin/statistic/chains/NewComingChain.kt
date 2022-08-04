package statistic.chains

import Storages
import chain.Chain
import core.Updating
import executables.Executable
import handlers.CommandEvent
import statistic.storage.StatisticHandling
import statistic.storage.StatisticType

class NewComingChain(
    private val mStatistic: StatisticHandling
) : Chain(CommandEvent("/start")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        Storages.stUsersStorage.rewrittenUser(updating)
        mStatistic.writeStatistic(
            updating,
            StatisticType.NewComing
        )
        return listOf(Executable.Dummy())
    }
}
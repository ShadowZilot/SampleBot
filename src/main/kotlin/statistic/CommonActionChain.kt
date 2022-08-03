package statistic

import chain.Chain
import core.Updating
import executables.Executable

class CommonActionChain(
    private val mStatistic: StatisticHandling
) : Chain {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        Storages.stUsersStorage.rewrittenUser(updating)
        mStatistic.writeStatistic(
            updating,
            StatisticType.CommonAction
        )
        return emptyList()
    }
}
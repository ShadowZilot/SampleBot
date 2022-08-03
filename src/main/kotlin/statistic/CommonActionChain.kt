package statistic

import chain.Chain
import core.Updating
import executables.Executable
import handlers.BotRecognizerEvent

class CommonActionChain(
    private val mStatistic: StatisticHandling
) : Chain(BotRecognizerEvent.Dummy) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        Storages.stUsersStorage.rewrittenUser(updating)
        mStatistic.writeStatistic(
            updating,
            StatisticType.CommonAction
        )
        return emptyList()
    }
}
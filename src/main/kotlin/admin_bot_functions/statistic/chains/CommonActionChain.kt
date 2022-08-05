package admin_bot_functions.statistic.chains

import chain.Chain
import core.Updating
import executables.Executable
import handlers.BotRecognizerEvent
import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType

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
package statistic

import chain.Chain
import core.Updating
import executables.Executable
import handlers.BotRecognizerEvent
import handlers.UnhandledEvent

class NewComingChain(
    private val mEvent: BotRecognizerEvent,
    private val mStatistic: StatisticHandling
) : Chain {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return try {
            updating.map(mEvent)
            Storages.stUsersStorage.rewrittenUser(updating)
            mStatistic.writeStatistic(
                updating,
                StatisticType.NewComing
            )
            return listOf(Executable.Dummy())
        } catch (e: UnhandledEvent) {
            emptyList()
        }
    }
}
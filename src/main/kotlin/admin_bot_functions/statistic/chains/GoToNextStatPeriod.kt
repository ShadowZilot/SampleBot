package admin_bot_functions.statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import admin_bot_functions.statistic.StatisticMessage
import admin_bot_functions.statistic.period_time.StatisticsTimePeriod
import updating.UpdatingMessageId

class GoToNextStatPeriod(
    private val mStatPeriod: StatisticsTimePeriod,
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten("nextStatPeriod")
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStatPeriod.goToNextPeriod(updating)
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к следующему промежутку"
            ),
            mStatisticMessage.message(
                updating,
                updating.map(UpdatingMessageId())
            )
        )
    }
}
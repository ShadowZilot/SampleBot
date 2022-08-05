package statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import statistic.StatisticMessage
import statistic.period_time.StatisticsTimePeriod
import updating.UpdatingMessageId

class GoToPreviousStatPeriod(
    private val mStatPeriod: StatisticsTimePeriod,
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten("previousStatPeriod")
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStatPeriod.goToPreviousPeriod(updating)
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к предыдущему промежутку"
            ),
            mStatisticMessage.message(
                updating,
                updating.map(UpdatingMessageId())
            )
        )
    }
}
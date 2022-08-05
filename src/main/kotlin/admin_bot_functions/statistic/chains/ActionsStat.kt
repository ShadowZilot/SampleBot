package admin_bot_functions.statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import admin_bot_functions.statistic.StatisticMessage
import updating.UpdatingMessageId

class ActionsStat(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten("actionsStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putString("statType", "actions")
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике взаимодействий"
            ),
            mStatisticMessage.message(
                updating,
                updating.map(UpdatingMessageId())
            )
        )
    }
}
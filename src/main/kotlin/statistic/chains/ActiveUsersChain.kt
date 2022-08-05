package statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import statistic.StatisticMessage
import updating.UpdatingMessageId

class ActiveUsersChain(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten("activeUsersStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putString("statType", "activeUsers")
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике активных пользователей"
            ),
            mStatisticMessage.message(
                updating,
                updating.map(UpdatingMessageId())
            )
        )
    }
}
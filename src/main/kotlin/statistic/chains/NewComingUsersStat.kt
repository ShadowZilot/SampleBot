package statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import statistic.StatisticMessage
import updating.UpdatingMessageId

class NewComingUsersStat(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten("newUsersStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putString("statType", "newUsers")
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике новых пользователей"
            ),
            mStatisticMessage.message(
                updating,
                updating.map(UpdatingMessageId())
            )
        )
    }
}
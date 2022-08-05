package admin_bot_functions.statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten
import admin_bot_functions.statistic.StatisticMessage
import updating.UpdatingCallbackData
import updating.UpdatingMessageId

class CancelEnteringDateChain(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnCallbackGotten()
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val callbackData = updating.map(UpdatingCallbackData())
        return if (callbackData == "cancelEnteringStartDate" || callbackData == "cancelEnteringEndDate") {
            mStates.state(updating).editor(mStates).apply {
                deleteValue(
                    if (callbackData == "cancelEnteringStartDate") {
                        "isWaitForStartDate"
                    } else {
                        "isWaitForEndDate"
                    }
                )
            }.commit()
            listOf(
                AnswerToCallback(
                    mKey,
                    "Отменяю ввод"
                ),
                mStatisticMessage.message(
                    updating,
                    updating.map(UpdatingMessageId())
                )
            )
        } else {
            emptyList()
        }
    }
}
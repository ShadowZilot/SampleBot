package statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.ToMarkdownSupported
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import statistic.period_time.StatisticsTimePeriod
import updating.UpdatingCallbackData

private val mStatKeyboard = InlineKeyboardMarkup(
    listOf(
        listOf(
            InlineButton(
                "Предыдущий промежуток",
                mCallbackData = "previousStatPeriod"
            ),
            InlineButton(
                "Следующий промежуток",
                mCallbackData = "nextStatPeriod"
            )
        ),
        listOf(
            InlineButton(
                "Указать начальную дату",
                mCallbackData = "selectStartStatDate"
            ),
            InlineButton(
                "Указать конечную дату",
                mCallbackData = "selectEndStatDate"
            )
        ),
        listOf(
            InlineButton(
                "Вернуться",
                mCallbackData = "backToStatistic"
            )
        )
    )
)

class CancelEnteringDateChain(
    private val mStatPeriod: StatisticsTimePeriod
) : Chain(
    OnCallbackGotten()
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val callbackData = updating.map(UpdatingCallbackData())
        return if (callbackData == "cancelEnteringStartDate" || callbackData == "cancelEnteringEndDate") {
            val statPeriod = mStatPeriod.statisticPeriodText(updating)
            mStates.state(updating).editor(mStates).apply {
                deleteValue(
                    if (callbackData == "cancelEnteringStartDate") {
                        "isWaitForStartDate"
                    } else {
                        "isWaitForEndDate"
                    }
                )
            }.commit()
            val type = mStates.state(updating).string("statType")
            listOf(
                AnswerToCallback(
                    mKey,
                    "Отменяю ввод"
                ),
                when (type) {
                    "newUsers" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Новые пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Статистика")
                        },
                        mMarkup = mStatKeyboard
                    )
                    "actions" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Взаимодействия*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Статистика")
                        },
                        mMarkup = mStatKeyboard
                    )
                    "activeUsers" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Активные пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Статистика")
                        },
                        mMarkup = mStatKeyboard
                    )
                    else -> Executable.Dummy()
                }
            )
        } else {
            emptyList()
        }
    }
}
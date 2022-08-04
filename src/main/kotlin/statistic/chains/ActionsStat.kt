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

class ActionsStat(
    private val mStatisticPeriod: StatisticsTimePeriod
) : Chain(
    OnCallbackGotten("actionsStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val statPeriod = mStatisticPeriod.statisticPeriodText(updating)
        mStates.state(updating).editor(mStates).apply {
            putString("statType", "actions")
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике взаимодействий"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Статистика*")
                    appendLine("*Взаимодействия*")
                    append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                    appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                    appendLine()
                    appendLine("Статистика")
                },
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        listOf(
                            InlineButton(
                                "Предыдущий",
                                mCallbackData = "previousStatPeriod"
                            ),
                            InlineButton(
                                "Следующий",
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
            )
        )
    }
}
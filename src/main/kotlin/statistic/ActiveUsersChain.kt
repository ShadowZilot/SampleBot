package statistic

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.ToMarkdownSupported
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class ActiveUsersChain(
    private val mStatisticPeriod: StatisticsTimePeriod
) : Chain(
    OnCallbackGotten("activeUsersStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val statPeriod = mStatisticPeriod.statisticPeriodText(updating)
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике активных пользователей"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Статистика*")
                    appendLine("*Активные пользователи*")
                    append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                    appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                    appendLine()
                    appendLine("Статистика")
                },
                mMarkup = InlineKeyboardMarkup(
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
                                mCallbackData = "selectEndStatDate"
                            ),
                            InlineButton(
                                "Указать конечную дату",
                                mCallbackData = "selectStartStatDate"
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
package admin_bot_functions.statistic.chains

import admin_bot_functions.statistic.form_stat.StatFormForAllTimeUsers
import admin_bot_functions.statistic.storage.StatisticHandling
import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class AllTimeUsersChain(
    private val mStatistic: StatisticHandling
) : Chain(
    OnCallbackGotten("allTimeUsers")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к общему количеству пользователей"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Статистика*")
                    appendLine("*Количество пользователей*")
                    appendLine()
                    appendLine("Общее количество пользователей\\: ")
                    append("${StatFormForAllTimeUsers(mStatistic).statisticValue(updating)}")
                },
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            "Вернуться",
                            mCallbackData = "backToStatistic"
                        )
                    ).convertToVertical()
                )
            )
        )
    }
}
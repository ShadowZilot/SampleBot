package admin_bot_functions.statistic.chains

import admin_bot_functions.statistic.form_stat.StatFormForAllTimeUsers
import admin_bot_functions.statistic.form_stat.StatFormForRealTimeUsers
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

class RealTimeUsersChain(
    private val mStatistic: StatisticHandling
) : Chain(
    OnCallbackGotten("realTimeUsers")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к пользователям за 30 минут"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Статистика*")
                    appendLine("*Пользователи за 30 минут*")
                    appendLine()
                    appendLine("Количество активных пользователей за последние 30 минут\\: ")
                    append("${StatFormForRealTimeUsers(mStatistic).statisticValue(updating)}")
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
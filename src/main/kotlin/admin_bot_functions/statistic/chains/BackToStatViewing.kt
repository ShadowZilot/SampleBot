package admin_bot_functions.statistic.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class BackToStatViewing : Chain(
    OnCallbackGotten("backToStatistic")
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                ""
            ),
            EditTextMessage(
                mKey,
                "*Статистика*\n" +
                        "Здесь представлены разделы по которым вы можете посмотреть статистику",
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            "Активные пользователи",
                            mCallbackData = "activeUsersStatistic"
                        ),
                        InlineButton(
                            "Новые пользователи",
                            mCallbackData = "newUsersStatistic"
                        ),
                        InlineButton(
                            "Взаимодействия",
                            mCallbackData = "actionsStatistic"
                        )
                    ).convertToVertical()
                )
            )
        )
    }
}
package statistic

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class ActiveUsersChain : Chain(
    OnCallbackGotten("activeUsersStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике активных пользователей"
            ),
            EditTextMessage(
                mKey,
                "Здесь скоро будет показываться статистика",
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
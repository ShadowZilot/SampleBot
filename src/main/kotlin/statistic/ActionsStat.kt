package statistic

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class ActionsStat : Chain(
    OnCallbackGotten("actionsStatistic")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к статистике взаимодействий"
            ),
            EditTextMessage(
                mKey,
                "Здесь скоро будет показываться статистика",
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
                                "",
                                mCallbackData = "selectEndStatDate"
                            ),
                            InlineButton(
                                "",
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
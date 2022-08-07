package admin_bot_functions.base.chains

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class BackToAdminPanelChain : Chain(
    OnCallbackGotten("backToAdminPanel")
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к панели админа"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Панель админа*")
                    appendLine()
                    appendLine("Выбирите один из разделов чтобы перейти в него")
                },
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            "Статистика",
                            mCallbackData = "startViewStat"
                        ),
                        InlineButton(
                            "Формирование ссылок",
                            mCallbackData = "goToLinkEditor"
                        )
                    ).convertToVertical()
                )
            )
        )
    }
}
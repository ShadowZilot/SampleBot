package admin_bot_functions.base.chains

import chain.Chain
import core.Updating
import executables.Executable
import executables.SendMessage
import handlers.CommandEvent
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

class ViewAdminPanelChain : Chain(
    CommandEvent("/admin_panel")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            SendMessage(
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
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putLong("adminPanelMessage", it.toLong())
                }.commit()
            }
        )
    }
}
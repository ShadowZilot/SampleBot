package admin_bot_functions.deeplinking.chain

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.EditTextMessage
import executables.Executable
import handlers.OnCallbackGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import updating.UpdatingMessageId

class BeginCreateDeeplinkChain : Chain(
    OnCallbackGotten("createDeeplink")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("isAwaitForDeeplinkName", true)
            putInt(
                "deeplinkEditorMessage",
                updating.map(UpdatingMessageId()).toInt()
            )
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Создание новой ссылки"
            ),
            EditTextMessage(
                mKey,
                buildString {
                    appendLine("*Создание ссылки*")
                    appendLine()
                    appendLine("Введите имя новой ссылки\\.")
                    appendLine(
                        "_Пояснение\\: имя будет отображаться в списке ссылок\\." +
                                " Следует назвать ссылку осмысленно\\. Лучше придумать название которое будет" +
                                " описывать место где вы будете публиковать эту ссылку\\." +
                                " В списке будет показано количество человек перешедших в бота по этой ссылке\\._"
                    )
                },
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            "Отменить",
                            mCallbackData = "cancelCreatingLink"
                        )
                    ).convertToVertical()
                )
            )
        )
    }
}
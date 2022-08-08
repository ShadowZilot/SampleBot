package admin_bot_functions.deeplinking.chain

import admin_bot_functions.deeplinking.storage.DeeplinkStorage
import chain.Chain
import core.Updating
import executables.DeleteMessage
import executables.EditTextMessage
import executables.Executable
import handlers.OnTextGotten
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import staging.safetyBoolean
import updating.UpdatingMessage

class CreateDeeplinkFinalChain(
    private val mDeeplink: DeeplinkStorage
) : Chain(
    OnTextGotten()
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("isAwaitForDeeplinkName")) {
            mDeeplink.createNewDeeplink(
                updating.map(UpdatingMessage())
            )
            mStates.state(updating).editor(mStates).apply {
                deleteValue("isAwaitForDeeplinkName")
            }.commit()
            listOf(
                DeleteMessage(
                    mKey,
                    updating
                ),
                EditTextMessage(
                    mKey,
                    "Отлично\\! Ссылка создана\\!",
                    mMarkup = InlineKeyboardMarkup(
                        listOf(
                            InlineButton(
                                "Вернуться",
                                mCallbackData = "goToLinkEditor"
                            )
                        ).convertToVertical()
                    ),
                    mEditingMessageId = mStates.state(updating).int(
                        "deeplinkEditorMessage"
                    ).toLong()
                )
            )
        } else {
            emptyList()
        }
    }
}
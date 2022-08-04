package statistic.chains

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

class PointEndDateChain : Chain(
    OnCallbackGotten("selectEndStatDate")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("isWaitForEndDate", true)
            putLong("statMessageId", updating.map(UpdatingMessageId()))
        }.commit()
        return listOf(
            AnswerToCallback(mKey),
            EditTextMessage(
                mKey,
                "Укажите конечную дату, с которой будет учитываться статистика\\." +
                        " Дата указывается в формате дд\\.мм\\.гггг",
                mMarkup = InlineKeyboardMarkup(
                    listOf(
                        InlineButton(
                            "Отменить",
                            mCallbackData = "cancelEnteringStartDate"
                        )
                    ).convertToVertical()
                )
            )
        )
    }
}
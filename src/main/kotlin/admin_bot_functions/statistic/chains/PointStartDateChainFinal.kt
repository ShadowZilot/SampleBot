package admin_bot_functions.statistic.chains

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
import admin_bot_functions.statistic.StatisticMessage
import admin_bot_functions.statistic.period_time.DateExtractor
import admin_bot_functions.statistic.period_time.WrongTimeFormat
import updating.UpdatingMessage

class PointStartDateChainFinal(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnTextGotten()
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("isWaitForStartDate")) {
            try {
                val date = DateExtractor.Base(updating.map(UpdatingMessage())).extract()
                mStates.state(updating).editor(mStates).apply {
                    putLong("statStartPeriodDate", date)
                    deleteValue("isWaitForStartDate")
                }.commit()
                listOf(
                    DeleteMessage(mKey, updating),
                    mStatisticMessage.message(
                        updating,
                        mStates.state(updating).int("statMessageId").toLong()
                    )
                )
            } catch (e: WrongTimeFormat) {
                listOf(
                    EditTextMessage(
                        mKey,
                        "Не могу разобрать что вы написали\\!\n" +
                                "Пожалуйста отправьте дату в формате дд\\.мм\\.гггг",
                        mMarkup = InlineKeyboardMarkup(
                            listOf(
                                InlineButton(
                                    "Отменить",
                                    mCallbackData = "cancelEnteringStartDate"
                                )
                            ).convertToVertical()
                        ),
                        mEditingMessageId = mStates.state(updating).int("statMessageId").toLong()
                    )
                )
            }
        } else {
            emptyList()
        }
    }
}
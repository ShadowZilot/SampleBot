package statistic.chains

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
import statistic.StatisticMessage
import statistic.period_time.DateExtractor
import statistic.period_time.WrongTimeFormat
import updating.UpdatingMessage

class PointEndDateChainFinal(
    private val mStatisticMessage: StatisticMessage
) : Chain(
    OnTextGotten()
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (mStates.state(updating).safetyBoolean("isWaitForEndDate")) {
            try {
                val date = DateExtractor.Base(updating.map(UpdatingMessage())).extract()
                mStates.state(updating).editor(mStates).apply {
                    putLong("statEndPeriodDate", date)
                    deleteValue("isWaitForEndDate")
                }.commit()
                listOf(
                    DeleteMessage(mKey, updating),
                    mStatisticMessage.message(
                        updating,
                        mStates.state(updating).long("statMessageId")
                    )
                )
            } catch (e: WrongTimeFormat) {
                listOf(
                    EditTextMessage(
                        mKey,
                        "Немогу разобрать что вы написали\\!\n" +
                                "Пожалуйста отправьте дату в формате дд\\.мм\\.гггг",
                        mMarkup = InlineKeyboardMarkup(
                            listOf(
                                InlineButton(
                                    "Отменить",
                                    mCallbackData = "cancelEnteringStartDate"
                                )
                            ).convertToVertical()
                        ),
                        mEditingMessageId = mStates.state(updating).long("statMessageId")
                    )
                )
            }
        } else {
            emptyList()
        }
    }
}
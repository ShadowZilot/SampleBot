package statistic.chains

import chain.Chain
import core.Updating
import executables.DeleteMessage
import executables.EditTextMessage
import executables.Executable
import handlers.OnTextGotten
import helpers.ToMarkdownSupported
import helpers.convertToVertical
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import staging.safetyBoolean
import statistic.period_time.DateExtractor
import statistic.period_time.StatisticsTimePeriod
import statistic.period_time.WrongTimeFormat
import updating.UpdatingMessage

private val mStatKeyboard = InlineKeyboardMarkup(
    listOf(
        listOf(
            InlineButton(
                "Предыдущий промежуток",
                mCallbackData = "previousStatPeriod"
            ),
            InlineButton(
                "Следующий промежуток",
                mCallbackData = "nextStatPeriod"
            )
        ),
        listOf(
            InlineButton(
                "Указать начальную дату",
                mCallbackData = "selectStartStatDate"
            ),
            InlineButton(
                "Указать конечную дату",
                mCallbackData = "selectEndStatDate"
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

class PointStartDateChainFinal(
    private val mStatPeriod: StatisticsTimePeriod
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
                val type = mStates.state(updating).string("statType")
                val statPeriod = mStatPeriod.statisticPeriodText(updating)
                listOf(
                    DeleteMessage(mKey, updating),
                    when (type) {
                        "newUsers" -> EditTextMessage(
                            mKey,
                            buildString {
                                appendLine("*Статистика*")
                                appendLine("*Новые пользователи*")
                                append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                                appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                                appendLine()
                                appendLine("Статистика")
                            },
                            mMarkup = mStatKeyboard
                        )
                        "actions" -> EditTextMessage(
                            mKey,
                            buildString {
                                appendLine("*Статистика*")
                                appendLine("*Взаимодействия*")
                                append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                                appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                                appendLine()
                                appendLine("Статистика")
                            },
                            mMarkup = mStatKeyboard
                        )
                        "activeUsers" -> EditTextMessage(
                            mKey,
                            buildString {
                                appendLine("*Статистика*")
                                appendLine("*Активные пользователи*")
                                append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                                appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                                appendLine()
                                appendLine("Статистика")
                            },
                            mMarkup = mStatKeyboard,
                            mEditingMessageId = mStates.state(updating).long("statMessageId")
                        )
                        else -> Executable.Dummy()
                    }
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
                        )
                    )
                )
            }
        } else {
            emptyList()
        }
    }
}
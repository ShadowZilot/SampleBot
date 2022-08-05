package statistic

import core.Updating
import executables.EditTextMessage
import executables.Executable
import executables.SendMessage
import helpers.ToMarkdownSupported
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup
import staging.StateHandling
import statistic.form_stat.StatFormForActions
import statistic.form_stat.StatFormForActiveUsers
import statistic.form_stat.StatFormForNewComing
import statistic.period_time.StatisticsTimePeriod
import statistic.storage.StatisticHandling

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

interface StatisticMessage {

    fun message(updating: Updating, messageId: Long = -1L): Executable

    class Base(
        private val mKey: String,
        private val mStates: StateHandling,
        private val mStatistic: StatisticHandling
    ) : StatisticMessage {
        private val mPeriod = StatisticsTimePeriod.Base(
            mStates
        )

        override fun message(updating: Updating, messageId: Long): Executable {
            val statPeriod = mPeriod.statisticPeriodText(updating)
            val type = mStates.state(updating).string("statType")
            return if (messageId == -1L) {
                when (type) {
                    "newUsers" -> SendMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Новые пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Новые пользователи за это промежуток\\: ")
                            append("${StatFormForNewComing(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard
                    )
                    "actions" -> SendMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Взаимодействия*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Взаимодействия за это промежуток\\: ")
                            append("${StatFormForActions(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard
                    )
                    "activeUsers" -> SendMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Активные пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Активные пользователи за это промежуток\\: ")
                            append("${StatFormForActiveUsers(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard
                    )
                    else -> Executable.Dummy()
                }
            } else {
                when (type) {
                    "newUsers" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Новые пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Новые пользователи за это промежуток\\: ")
                            append("${StatFormForNewComing(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard,
                        mEditingMessageId = messageId
                    )
                    "actions" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Взаимодействия*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Взаимодействия за это промежуток\\: ")
                            append("${StatFormForActions(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard,
                        mEditingMessageId = messageId
                    )
                    "activeUsers" -> EditTextMessage(
                        mKey,
                        buildString {
                            appendLine("*Статистика*")
                            appendLine("*Активные пользователи*")
                            append("C *${ToMarkdownSupported.Base(statPeriod.first).convertedString()}*")
                            appendLine(" по *${ToMarkdownSupported.Base(statPeriod.second).convertedString()}*")
                            appendLine()
                            appendLine("Активные пользователи за это промежуток\\: ")
                            append("${StatFormForActiveUsers(mStatistic, mPeriod).statisticValue(updating)}")
                        },
                        mMarkup = mStatKeyboard,
                        mEditingMessageId = messageId
                    )
                    else -> Executable.Dummy()
                }
            }
        }
    }
}
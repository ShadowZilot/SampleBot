package admin_bot_functions.statistic.form_stat

import admin_bot_functions.statistic.storage.StatisticItem
import admin_bot_functions.statistic.storage.StatisticType

class IsStatItemFit(
    private val mDateRange: LongRange,
    private val mType: StatisticType
) : StatisticItem.Mapper<Boolean> {
    override fun map(
        userId: Long,
        chatId: Long,
        eventName: String,
        parameters: List<Pair<String, Any>>,
        date: Long
    ) = date in mDateRange && mType.typeName() == eventName
}
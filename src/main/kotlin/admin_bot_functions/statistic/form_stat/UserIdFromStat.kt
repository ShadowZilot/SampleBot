package admin_bot_functions.statistic.form_stat

import admin_bot_functions.statistic.storage.StatisticItem

class UserIdFromStat : StatisticItem.Mapper<Long> {
    override fun map(
        id: Int,
        userId: Long,
        chatId: Long,
        eventName: String,
        parameters: List<Pair<String, Any>>,
        date: Long
    ) = userId
}
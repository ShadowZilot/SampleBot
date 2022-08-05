package statistic.form_stat

import statistic.storage.StatisticItem

class UserIdFromStat : StatisticItem.Mapper<Long> {
    override fun map(
        userId: Long,
        chatId: Long,
        eventName: String,
        parameters: List<Pair<String, Any>>,
        date: Long
    ) = userId
}
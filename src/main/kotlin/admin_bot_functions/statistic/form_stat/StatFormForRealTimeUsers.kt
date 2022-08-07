package admin_bot_functions.statistic.form_stat

import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType
import core.Updating

class StatFormForRealTimeUsers(
    private val mStats: StatisticHandling
) : StatisticForm {

    override fun statisticValue(updating: Updating): Int {
        val time = System.currentTimeMillis()
        val slice = mStats.statSliceByDate(
            StatisticType.CommonAction,
            (time - 1800_000)..time
        )
        val users = mutableListOf<Long>()
        for (i in slice.indices) {
            if (!users.contains(slice[i].map(UserIdFromStat()))) {
                users.add(slice[i].map(UserIdFromStat()))
            }
        }
        return users.size
    }
}
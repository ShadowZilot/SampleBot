package admin_bot_functions.statistic.form_stat

import admin_bot_functions.statistic.storage.StatisticHandling
import core.Updating

class StatFormForAllTimeUsers(
    private val mStats: StatisticHandling
) : StatisticForm {

    override fun statisticValue(updating: Updating): Int {
        val allStats = mStats.allStats()
        val users = mutableListOf<Long>()
        for (i in allStats.indices) {
            if (!users.contains(allStats[i].map(UserIdFromStat()))) {
                users.add(allStats[i].map(UserIdFromStat()))
            }
        }
        return users.size
    }
}
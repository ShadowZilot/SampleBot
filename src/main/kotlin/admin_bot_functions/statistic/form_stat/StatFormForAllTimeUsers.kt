package admin_bot_functions.statistic.form_stat

import admin_bot_functions.statistic.storage.StatisticHandling
import core.Updating

class StatFormForAllTimeUsers(
    private val mStats: StatisticHandling
) : StatisticForm {

    override fun statisticValue(updating: Updating): Int {
        return mStats.allUsers()
    }
}
package admin_bot_functions.statistic.form_stat

import core.Updating

interface StatisticForm {

    fun statisticValue(updating: Updating): Int
}
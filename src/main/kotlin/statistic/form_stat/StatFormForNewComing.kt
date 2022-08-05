package statistic.form_stat

import core.Updating
import statistic.period_time.StatisticsTimePeriod
import statistic.storage.StatisticHandling
import statistic.storage.StatisticType

class StatFormForNewComing(
    private val mStats: StatisticHandling,
    private val mPeriod: StatisticsTimePeriod
) : StatisticForm {

    override fun statisticValue(updating: Updating): Int {
        val slice = mStats.statSliceByDate(
            StatisticType.NewComing,
            mPeriod.dateRange(updating)
        )
        return slice.size
    }
}
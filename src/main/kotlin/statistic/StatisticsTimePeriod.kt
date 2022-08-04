package statistic

import core.Updating
import staging.NotFoundStateValue
import staging.StateHandling

interface StatisticsTimePeriod {

    fun setupDefaultPeriod(updating: Updating)

    class Base(
        private val mStates: StateHandling
    ) : StatisticsTimePeriod {

        override fun setupDefaultPeriod(updating: Updating) {
            val currentTime = System.currentTimeMillis()
            try {
                mStates.state(updating).long("statStartPeriodDate")
            } catch (e: NotFoundStateValue) {
                mStates.state(updating).editor(mStates).apply {
                    putLong(
                        "statStartPeriodDate",
                        currentTime.startDayTime()
                    )
                }.commit()
            }
            try {
                mStates.state(updating).long("statEndPeriodDate")
            } catch (e: NotFoundStateValue) {
                mStates.state(updating).editor(mStates).apply {
                    putLong(
                        "statEndPeriodDate",
                        currentTime.endDayTime()
                    )
                }.commit()
            }
        }
    }
}
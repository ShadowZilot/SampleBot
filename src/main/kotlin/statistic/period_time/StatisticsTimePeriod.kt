package statistic.period_time

import core.Updating
import staging.NotFoundStateValue
import staging.StateHandling
import java.text.SimpleDateFormat
import java.util.*

interface StatisticsTimePeriod {

    fun setupDefaultPeriod(updating: Updating)

    fun statisticPeriodText(updating: Updating): Pair<String, String>

    fun goToNextPeriod(updating: Updating)

    fun goToPreviousPeriod(updating: Updating)

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

        override fun statisticPeriodText(updating: Updating): Pair<String, String> {
            val startDate = mStates.state(updating).long("statStartPeriodDate")
            val endDate = mStates.state(updating).long("statEndPeriodDate")
            val formatter = SimpleDateFormat(
                "dd.LL.yyyy"
            )
            return Pair(
                formatter.format(Date(startDate)),
                formatter.format(Date(endDate))
            )
        }

        override fun goToNextPeriod(updating: Updating) {
            val startDate = mStates.state(updating).long("statStartPeriodDate")
            val endDate = mStates.state(updating).long("statEndPeriodDate")
            val newEndDate = endDate + (endDate - startDate)
            mStates.state(updating).editor(mStates).apply {
                putLong(
                    "statStartPeriodDate",
                    endDate
                )
                putLong(
                    "statEndPeriodDate",
                    newEndDate
                )
            }.commit()
        }

        override fun goToPreviousPeriod(updating: Updating) {
            val startDate = mStates.state(updating).long("statStartPeriodDate")
            val endDate = mStates.state(updating).long("statEndPeriodDate")
            val newStartDate = startDate - (endDate - startDate)
            mStates.state(updating).editor(mStates).apply {
                putLong(
                    "statEndPeriodDate",
                    startDate
                )
                putLong(
                    "statStartPeriodDate",
                    newStartDate
                )
            }.commit()
        }
    }
}
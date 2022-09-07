import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType

interface StatisticGenerator {

    fun generate()

    class Base(
        private val mDatabases: StatisticHandling
    ) : StatisticGenerator {

        override fun generate() {
            var startTime = 1659348000000
            while (startTime < 1669888800000) {
                mDatabases.writeStatistic(
                    (10000..99999).random().toLong(),
                    (-99999..99999).random().toLong(),
                    StatisticType.CommonAction
                )
                startTime += 10_000
            }
        }
    }
}
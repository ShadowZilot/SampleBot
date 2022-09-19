import admin_bot_functions.statistic.storage.StatisticFileHandling
import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType
import helpers.storage.edb_commons.EDBConnection
import kotlin.test.BeforeTest
import kotlin.test.Test

class StatisticTesting {
    private val mDB = StatisticHandling.Base(
        StatisticFileHandling(
            EDBConnection.Base("statisticsTest.edb")
        )
    )

    @BeforeTest
    fun dbSetup() {
        SpeedTesting.Base(
            {
                StatisticGenerator.Base(
                    mDB
                ).generate()
            },
            "Database generating",
            5_000L
        ).test()
    }

    @Test
    fun baseTest() {
        val actualTime = SpeedTesting.Base(
            {
                println(
                    mDB.statSliceByDate(
                        StatisticType.CommonAction,
                        1664964000000..1668160800000
                    )
                )
            },
            "Base selecting",
            10L
        ).test()
        assert(actualTime <= 10L)
    }
}
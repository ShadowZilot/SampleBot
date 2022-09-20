import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType
import helpers.storage.jdbc_wrapping.DatabaseHelper
import kotlin.test.BeforeTest
import kotlin.test.Test

class StatisticTesting {
    private val mDatabase = DatabaseHelper.Base.Instance.provideInstance()
    private val mDB = StatisticHandling.Base(
        "statistics",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }

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
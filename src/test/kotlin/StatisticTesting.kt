import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.storage.StatisticType
import helpers.storage.jdbc_wrapping.DatabaseHelper
import kotlin.test.Test

class StatisticTesting {
    private val mDatabase = DatabaseHelper.Base.Instance.provideInstance()
    private val mDB = StatisticHandling.Base(
        "statistics_test",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
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
            500L
        ).test()
        assert(actualTime <= 500L)
    }

    @Test
    fun countTest() {
        println("Count users ${mDB.allUsers()}")
    }
}
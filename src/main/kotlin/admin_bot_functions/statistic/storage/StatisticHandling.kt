package admin_bot_functions.statistic.storage

import core.Updating
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import updating.UpdatingChatId
import updating.UserIdUpdating

interface StatisticHandling : StorageShell {

    fun statSliceByDate(statType: StatisticType, dateRange: LongRange): List<StatisticItem>

    fun allUsers(): Int

    fun writeStatistic(
        userId: Long,
        chatId: Long,
        type: StatisticType,
        parameters: List<Pair<String, Any>> = emptyList()
    )

    fun writeStatistic(
        updating: Updating,
        type: StatisticType,
        parameters: List<Pair<String, Any>> = emptyList()
    )

    class Base(
        private val mTableName: String,
        private val mConnector: DatabaseHelper
    ) : StatisticHandling {

        override fun statSliceByDate(statType: StatisticType, dateRange: LongRange): List<StatisticItem> {
            return mutableListOf<StatisticItem>().apply {
                mConnector.executeQuery(
                    "select * from `$mTableName` where" +
                            " `stat_date` between ${dateRange.first} and ${dateRange.last}" +
                            " and `event_name` = '${statType.typeName()}'"
                ) { result, isEmpty ->
                    var isNextTrue = isEmpty
                    while (isNextTrue) {
                        add(StatisticItem(result))
                        isNextTrue = result.next()
                    }
                }
            }
        }

        override fun allUsers(): Int {
            var usersCount = 0
            mConnector.executeQuery(
                "select count(*) as count_users from `$mTableName`"
            ) { result, _ ->
                usersCount = result.getInt("count_users")
            }
            return usersCount
        }

        override fun writeStatistic(
            userId: Long,
            chatId: Long,
            type: StatisticType,
            parameters: List<Pair<String, Any>>
        ) {
            mConnector.executeQueryWithoutResult(
                StatisticItem(
                    -1,
                    userId,
                    chatId,
                    type.typeName(),
                    parameters,
                    System.currentTimeMillis()
                ).insertSQLQuery(mTableName)
            )
        }

        override fun writeStatistic(
            updating: Updating,
            type: StatisticType,
            parameters: List<Pair<String, Any>>
        ) {
            writeStatistic(
                updating.map(UserIdUpdating()),
                updating.map(UpdatingChatId()).second,
                type,
                parameters
            )
        }

        override fun tableSchema() = "create table `$mTableName`(" +
                "id int primary key auto_increment, " +
                "user_id int, " +
                "chat_id int, " +
                "event_name varchar(255), " +
                "parameters text, " +
                "stat_date bigint" +
                ");"

        override fun tableName() = mTableName
    }
}
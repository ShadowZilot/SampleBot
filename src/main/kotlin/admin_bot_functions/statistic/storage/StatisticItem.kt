package admin_bot_functions.statistic.storage

import helpers.storage.Record
import org.json.JSONArray
import java.sql.ResultSet

data class StatisticItem(
    private val mId: Int,
    private val mUserId: Long,
    private val mChatId: Long,
    private val mEventName: String,
    private val mParameters: List<Pair<String, Any>>,
    private val mDate: Long,
) : Record() {

    constructor(resultSet: ResultSet) : this(
        resultSet.getInt("id"),
        resultSet.getLong("user_id"),
        resultSet.getLong("chat_id"),
        resultSet.getString("event_name"),
        mutableListOf<Pair<String, Any>>().apply {
            val array = JSONArray(resultSet.getString("parameters"))
            for (i in 0 until array.length()) {
                add(
                    Pair(
                        array.getJSONObject(i).getString("name"),
                        array.getJSONObject(i).get("value")
                    )
                )
            }
        },
        resultSet.getLong("stat_date")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mUserId,
        mChatId,
        mEventName,
        mParameters,
        mDate
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            userId: Long,
            chatId: Long,
            eventName: String,
            parameters: List<Pair<String, Any>>,
            date: Long
        ): T
    }

    override fun insertSQLQuery(tableName: String) =
        "insert into `$tableName` (`user_id`, `chat_id`," +
                " `event_name`, `parameters`, `stat_date`)" +
                " values ($mUserId, $mChatId," +
                " '$mEventName', '$mParameters', $mDate)"

    override fun updateSQLQuery(tableName: String) = ""

    override fun deleteSQLQuery(tableName: String) =
        "delete from `$tableName` where `id` = $mId"
}
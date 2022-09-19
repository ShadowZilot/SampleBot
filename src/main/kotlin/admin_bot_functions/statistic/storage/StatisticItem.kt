package admin_bot_functions.statistic.storage

import helpers.storage.Record
import org.json.JSONObject

data class StatisticItem(
    private val mUserId: Long,
    private val mChatId: Long,
    private val mEventName: String,
    private val mParameters: List<Pair<String, Any>>,
    private val mDate: Long,
) : Record() {

    constructor(item: JSONObject) : this(
        item.getLong("userId"),
        item.getLong("chatId"),
        item.getString("eventName"),
        mutableListOf<Pair<String, Any>>().apply {
            val array = item.getJSONArray("parameters")
            for (i in 0 until array.length()) {
                add(
                    Pair(
                        array.getJSONObject(i).getString("name"),
                        array.getJSONObject(i).get("value")
                    )
                )
            }
        },
        item.getLong("date")
    )


    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mUserId,
        mChatId,
        mEventName,
        mParameters,
        mDate
    )

    interface Mapper<T> {
        fun map(
            userId: Long,
            chatId: Long,
            eventName: String,
            parameters: List<Pair<String, Any>>,
            date: Long
        ): T
    }
}
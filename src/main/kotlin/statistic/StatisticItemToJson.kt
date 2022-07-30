package statistic

import org.json.JSONArray
import org.json.JSONObject

class StatisticItemToJson : StatisticItem.Mapper<JSONObject> {

    override fun map(
        userId: Long,
        chatId: Long,
        eventName: String,
        parameters: List<Pair<String, Any>>,
        date: Long
    ) = JSONObject().apply {
        put("userId", userId)
        put("chatId", chatId)
        put("eventName", eventName)
        put("parameters", JSONArray().apply {
            for (i in parameters.indices) {
                put(JSONObject().apply {
                    put("name", parameters[i].first)
                    put("value", parameters[i].second)
                })
            }
        })
        put("date", date)
    }
}
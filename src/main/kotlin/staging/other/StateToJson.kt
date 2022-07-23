package stating

import org.json.JSONArray
import org.json.JSONObject

class StateToJson : State.Mapper<JSONObject> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>) = JSONObject().apply {
        put("user_id", userId)
        put("codes", JSONArray().apply {
            stateCode.forEach {
                this.put(JSONObject().apply {
                    put("code", it.first)
                    put("data", it.second)
                })
            }
        })
    }
}
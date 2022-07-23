package stating

import core.Updating
import org.json.JSONObject
import updating.UserIdUpdating

data class State(
    private val mUserId: Long,
    private val mStateCode: List<Pair<String, String>> = emptyList()
) {
    constructor(updating: Updating): this(updating.map(UserIdUpdating()))

    constructor(item: JSONObject) : this(
        item.getLong("user_id"),
        mutableListOf<Pair<String, String>>().apply {
            val array = item.getJSONArray("codes")
            for (i in 0 until array.length()) {
                this.add(
                    Pair(
                        array.getJSONObject(i).getString("code"),
                        array.getJSONObject(i).getString("data")
                    )
                )
            }
        }
    )

    fun <T> map(mapper: Mapper<T>): T {
        return mapper.map(
            mUserId,
            mStateCode
        )
    }

    interface Mapper<T> {
        fun map(
            userId: Long,
            stateCode: List<Pair<String, String>>
        ): T
    }
}
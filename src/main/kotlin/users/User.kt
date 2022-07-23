package users

import org.json.JSONObject

data class User(
    private val mId: Long,
    private val mUsername: String,
    private val mFirstName: String,
    private val mSecondName: String,
    private val mIsStarted: Boolean = false
) {
    constructor(item: JSONObject) : this(
        item.getLong("id"),
        item.getString("username"),
        item.getString("firstName"),
        item.getString("secondName"),
        item.getBoolean("isStarted")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mUsername,
        mFirstName,
        mSecondName,
        mIsStarted
    )

    interface Mapper<T> {
        fun map(
            id: Long,
            username: String,
            firstName: String,
            secondName: String,
            isStarted: Boolean
        ): T
    }
}
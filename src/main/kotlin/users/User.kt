package users

import helpers.storage.Record
import org.json.JSONObject

data class User(
    private val mId: Long,
    private val mUsername: String,
    private val mFirstName: String,
    private val mSecondName: String,
    private val mIsStarted: Boolean = false
) : Record() {
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

    override fun id(): Long {
        TODO("Not yet implemented")
    }

    override fun toData(): JSONObject {
        TODO("Not yet implemented")
    }

    override fun contentLength(): Int {
        TODO("Not yet implemented")
    }
}
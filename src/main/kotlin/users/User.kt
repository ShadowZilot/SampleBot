package users

import helpers.storage.Record
import java.sql.ResultSet

data class User(
    private val mId: Long,
    private val mUsername: String,
    private val mFirstName: String,
    private val mSecondName: String,
    private val mIsStarted: Boolean = false
) : Record() {

    constructor(result: ResultSet) : this(
        result.getLong("id"),
        result.getString("username"),
        result.getString("firstName"),
        result.getString("secondName"),
        result.getByte("isStarted") == 1.toByte()
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
package admin_bot_functions.deeplinking.storage

import helpers.storage.Record
import org.json.JSONObject

data class Deeplink(
    private val mName: String,
    private val mCode: String,
    private val mLink: String,
    private val mCountUsers: Int
) : Record() {
    constructor(item: JSONObject) : this(
        item.getString("name"),
        item.getString("code"),
        item.getString("link"),
        item.getInt("usersCount"),
    )

    fun <T> map(mapper: Mapper<T>) : T = mapper.map(
        mName,
        mCode,
        mLink,
        mCountUsers
    )

    interface Mapper<T> {
        fun map(
            name: String,
            code: String,
            link: String,
            countUsers: Int
        ) : T
    }

    override fun insertSQLQuery(tableName: String): String {
        TODO("Not yet implemented")
    }

    override fun updateSQLQuery(tableName: String): String {
        TODO("Not yet implemented")
    }

    override fun deleteSQLQuery(tableName: String): String {
        TODO("Not yet implemented")
    }
}
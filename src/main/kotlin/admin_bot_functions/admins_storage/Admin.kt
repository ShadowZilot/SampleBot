package admin_bot_functions.admins_storage

import core.Updating
import helpers.storage.Record
import updating.UpdatingChatId
import java.sql.ResultSet

data class Admin(
    private val mUserId: Long
) : Record() {
    constructor(updating: Updating) : this(
        updating.map(UpdatingChatId()).second
    )

    constructor(result: ResultSet) : this(
        result.getLong("user_id")
    )

    fun <T> map(mapper: Mapper<T>) : T = mapper.map(
        mUserId
    )

    interface Mapper<T> {
        fun map(
            userId: Long
        ) : T
    }

    override fun insertSQLQuery(tableName: String) =
        "insert into `$tableName` (`user_id`) values ($mUserId)"

    override fun updateSQLQuery(tableName: String) =
        "update `$tableName` set `user_id` = $mUserId"

    override fun deleteSQLQuery(tableName: String) =
        "delete from `$tableName` where `user_id` = $mUserId"
}
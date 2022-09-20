package admin_bot_functions.deeplinking.storage

import helpers.storage.Record
import java.sql.ResultSet

data class Deeplink(
    private val mId: Int,
    private val mName: String,
    private val mCode: String,
    private val mLink: String,
    private val mCountUsers: Int
) : Record() {
    constructor(resultSet: ResultSet) : this(
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getString("code"),
        resultSet.getString("link"),
        resultSet.getInt("count_users")
    )

    fun <T> map(mapper: Mapper<T>): T = mapper.map(
        mId,
        mName,
        mCode,
        mLink,
        mCountUsers
    )

    interface Mapper<T> {
        fun map(
            id: Int,
            name: String,
            code: String,
            link: String,
            countUsers: Int
        ): T
    }

    override fun insertSQLQuery(tableName: String) =
        "insert into `$tableName` (`name`, `code`, `link`," +
                " `link`, `count_users`)" +
                " values ('$mName', '$mCode', '$mLink', $mCountUsers)"

    override fun updateSQLQuery(tableName: String) =
        "update `$tableName` set `name` = '$mName'," +
                " `code` = '$mCode'," +
                " `link` = '$mLink'," +
                " `count_users` = $mCountUsers where `id` = $mId"

    override fun deleteSQLQuery(tableName: String) =
        "delete from `$tableName` where `id` = $mId"
}
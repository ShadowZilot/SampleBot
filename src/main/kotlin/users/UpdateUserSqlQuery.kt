package users

class UpdateUserSqlQuery(
    private val mTableName: String
) : User.Mapper<String> {

    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) =
        "update `$mTableName` set `username` = '$username'," +
                " firstName = '$firstName'," +
                " secondName = '$secondName'," +
                " isStarted = ${if (isStarted) 1 else 0}" +
                " where `id` = $id;"
}
package users

class InsertUserSqlQuery(
    private val mTableName: String
) : User.Mapper<String> {

    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) = "insert into $mTableName (`id`," +
            "`username`," +
            "`firstName`, " +
            "`secondName`," +
            "`isStarted`) values($id," +
            "'$username', "+
            "'$firstName'," +
            "'$secondName'," +
            "${if (isStarted) 1 else 0})"
}
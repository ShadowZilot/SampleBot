package users

class UpdateUserSqlQuery : User.Mapper<String> {

    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) =
        "update users set username = $username," +
                " firstName = $firstName," +
                " secondName = $secondName," +
                " isStarted = ${if (isStarted) 1 else 0}" +
                "where id = $id"
}
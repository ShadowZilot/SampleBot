package all_users_handling.storage

import users.User

class UserId : User.Mapper<Long> {
    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) = id
}
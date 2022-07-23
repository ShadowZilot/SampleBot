package all_users_handling.storage

import users.User

class UserSameId(
    private val mId: Long
) : User.Mapper<Boolean> {
    constructor(user: User): this(
        user.map(object : User.Mapper<Long> {
            override fun map(
                id: Long,
                username: String,
                firstName: String,
                secondName: String,
                isStarted: Boolean
            ): Long {
                return id
            }
        })
    )

    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) = mId == id
}
package all_users_handling.storage

import java.lang.RuntimeException

class UserNotFoundException(
    val mId: Long
) : RuntimeException() {
    override val message = "User with ID = $mId not found!"
}
package users

import all_users_handling.storage.UserNotFoundException
import core.Updating
import helpers.storage.UserFileHelping
import updating.UserFromUpdating

interface AllUsersStorage {

    suspend fun rewrittenUser(updating: Updating): User

    suspend fun allUsers(): List<User>

    suspend fun user(updating: Updating): User

    suspend fun updateUser(user: User)

    suspend fun indexOfUser(user: User): Int

    suspend fun userById(id: Long): User

    class Base(
        private val mFile: UserFileHelping
    ) : AllUsersStorage {

        override suspend fun rewrittenUser(updating: Updating): User {
            val createdUser = updating.map(UserFromUpdating())
//            val user = try {
//                mUsers[indexOfUser(createdUser)]
//            } catch (e: UserNotFoundException) {
//                mUsers.add(createdUser)
//                mFile.cache(mUsers)
//                createdUser
//            }
            return createdUser
        }

        override suspend fun allUsers() = emptyList<User>()

        override suspend fun user(updating: Updating): User {
//            return mUsers[indexOfUser(
//                updating.map(UserFromUpdating())
//            )]
            return updating.map(UserFromUpdating())
        }

        override suspend fun updateUser(user: User) {
//            val index = indexOfUser(user)
//            mUsers.removeAt(index)
//            mUsers.add(index, user)
//            mFile.cache(mUsers)
        }

        override suspend fun indexOfUser(user: User): Int {
//            val finder = UserSameId(user)
//            val gottenUser = mUsers.find {
//                it.map(finder)
//            }
//            return if (gottenUser != null) {
//                mUsers.indexOf(gottenUser)
//            } else {
//                throw UserNotFoundException(user.map(UserId()))
//            }
            return 0
        }

        override suspend fun userById(id: Long): User {
//            val finder = UserSameId(id)
//            return mUsers.find {
//                it.map(finder)
//            } ?: throw UserNotFoundException(id)
            throw UserNotFoundException(id)
        }
    }
}
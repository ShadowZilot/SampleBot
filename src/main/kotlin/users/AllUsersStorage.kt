package users

import all_users_handling.storage.UserId
import all_users_handling.storage.UserNotFoundException
import core.Updating
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import updating.UserFromUpdating

interface AllUsersStorage : StorageShell {

    suspend fun rewrittenUser(updating: Updating): User

    suspend fun user(updating: Updating): User

    suspend fun updateUser(user: User)

    suspend fun userById(id: Long): User

    class Base(
        private val mTableName: String,
        private val mConnector: DatabaseHelper
    ) : AllUsersStorage {

        override suspend fun rewrittenUser(updating: Updating): User {
            updateUser(user(updating))
            return userById(
                updating.map(UserFromUpdating()).map(UserId())
            )
        }

        override suspend fun user(updating: Updating): User {
            return updating.map(UserFromUpdating())
        }

        override suspend fun updateUser(user: User) {
            mConnector.executeQueryWithoutResult(
                user.map(UpdateUserSqlQuery())
            )
        }

        override suspend fun userById(id: Long) = try {
            User(
                mConnector.executeQuery(
                    "select * from $mTableName where id = $id"
                )
            )
        } catch (e: Exception) {
            throw UserNotFoundException(id)
        }

        override fun tableSchema() = "create table $mTableName(" +
                "id int primary key," +
                "username varchar(255)," +
                "firstName varchar(255)," +
                "secondName varchar(255)" +
                "isStarted bit default 0" +
                ");"

        override fun tableName() = mTableName
    }
}
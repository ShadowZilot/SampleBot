package users

import all_users_handling.storage.UserNotFoundException
import core.Updating
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import kotlinx.coroutines.runBlocking
import updating.UserFromUpdating
import updating.UserIdUpdating

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
            val user = updating.map(UserFromUpdating())
            mConnector.executeQuery(
                "select count(*) as total from $mTableName where id = ${updating.map(UserIdUpdating())}",
            ) {
                runBlocking {
                    if (it.getInt("total") == 1) {
                        updateUser(user)
                    } else {
                        mConnector.executeQueryWithoutResult(
                            user.map(InsertUserSqlQuery(mTableName))
                        )
                    }
                }
            }
            return user
        }

        override suspend fun user(updating: Updating): User {
            return updating.map(UserFromUpdating())
        }

        override suspend fun updateUser(user: User) {
            mConnector.executeQueryWithoutResult(
                user.map(UpdateUserSqlQuery(mTableName))
            )
        }

        override suspend fun userById(id: Long) = try {
            var user: User? = null
            mConnector.executeQuery(
                "select * from $mTableName where id = $id",
            ) {
                user = User(it)
            }
            user!!
        } catch (e: Exception) {
            throw UserNotFoundException(id)
        }

        override fun tableSchema() = "create table `$mTableName`(" +
                "`id` int primary key," +
                "`username` varchar(255)," +
                "`firstName` varchar(255)," +
                "`secondName` varchar(255)," +
                "`isStarted` bit not null default 0);"

        override fun tableName() = mTableName
    }
}
package admin_bot_functions.admins_storage

import core.Updating
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import updating.UserIdUpdating

interface AdminsHandling : StorageShell {

    fun addNewAdmin(updating: Updating)

    fun isUserAdmin(updating: Updating): Boolean

    class Base(
        private val mTableName: String,
        private val mConnector: DatabaseHelper
    ) : AdminsHandling {

        override fun addNewAdmin(updating: Updating) {
            if (!isUserAdmin(updating)) {
                mConnector.executeQueryWithoutResult(
                    Admin(updating).insertSQLQuery(mTableName)
                )
            }
        }

        override fun isUserAdmin(updating: Updating): Boolean {
            var isAdmin = false
            mConnector.executeQuery(
                "select count(*) as admin from `$mTableName`" +
                        " where `user_id` = ${updating.map(UserIdUpdating())}"
            ) { result, _ ->
                isAdmin = result.getInt("admin") == 1
            }
            return isAdmin
        }

        override fun tableSchema() = "create table `$mTableName` (" +
                "user_id int primary key" +
                ");"

        override fun tableName() = mTableName
    }
}
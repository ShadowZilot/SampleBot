package staging

import core.Updating
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import updating.UserIdUpdating
import java.sql.SQLException

interface StateHandling : StorageShell {

    fun writeState(state: State)

    fun updateState(state: State)

    fun state(id: Long): State

    fun state(updating: Updating): State

    fun deleteState(id: Long)

    class Base(
        private val mTableName: String,
        private val mConnector: DatabaseHelper
    ) : StateHandling {

        override fun writeState(state: State) {
            mConnector.executeQueryWithoutResult(
                state.insertSQLQuery(mTableName)
            )
        }

        override fun updateState(state: State) {
            mConnector.executeQuery(
                "select count(*) as total from" +
                        " `$mTableName` where `user_id` = ${state.mUserId}"
            ) {
                if (it.getInt("total") == 0) {
                    mConnector.executeQueryWithoutResult(
                        state.insertSQLQuery(mTableName)
                    )
                } else {
                    mConnector.executeQueryWithoutResult(
                        state.updateSQLQuery(mTableName)
                    )
                }
            }
        }

        override fun state(id: Long): State {
            var state: State? = null
            return try {
                mConnector.executeQuery(
                    "select * from `$mTableName` where `user_id` = $id"
                ) {
                    state = try {
                        State(it)
                    } catch (e: Exception) {
                        State(id, emptyList())
                    }
                }
                state ?: throw NotFoundState()
            } catch (e: SQLException) {
                State(id, emptyList())
            }
        }

        override fun state(updating: Updating): State {
            return state(updating.map(UserIdUpdating()))
        }

        override fun deleteState(id: Long) {
            mConnector.executeQueryWithoutResult(
                "delete * from `$mTableName` where `user_id` = $id"
            )
        }

        override fun tableSchema() = "create table `$mTableName`(" +
                "`user_id` int primary key, " +
                "`codes` text" +
                ");"

        override fun tableName() = mTableName
    }
}
package helpers.storage.jdbc_wrapping

import logs.Logging
import stConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

interface DatabaseHelper {

    fun executeQueryWithoutResult(sqlQuery: String)

    fun executeQuery(sqlQuery: String) : ResultSet

    class Base private constructor() : DatabaseHelper {
        private val mDBConnection : Connection

        init {
            try {
                mDBConnection = DriverManager.getConnection(
                    stConfig.configValueString("dbUrl"),
                    stConfig.configValueString("dbRoot"),
                    stConfig.configValueString("dbPassword")
                )
            } catch (e: SQLException) {
                throw Exception("Database connection failed")
            }
            if (!mDBConnection.isClosed) {
                Logging.ConsoleLog.log("Database connection successfully")
            }
        }

        override fun executeQueryWithoutResult(sqlQuery: String) {
            mDBConnection.createStatement().apply {
                execute(sqlQuery)
            }.close()
        }

        override fun executeQuery(sqlQuery: String): ResultSet {
            val statement = mDBConnection.createStatement()
            val result : ResultSet
            statement.use { st ->
                result = st.executeQuery(sqlQuery)
            }
            return result
        }

        object Instance {
            private var mInstance : DatabaseHelper? = null

            fun provideInstance() : DatabaseHelper {
                mInstance = if (mInstance == null) {
                    DatabaseHelper.Base()
                } else {
                    mInstance
                }
                return mInstance ?: throw Exception()
            }
        }
    }
}
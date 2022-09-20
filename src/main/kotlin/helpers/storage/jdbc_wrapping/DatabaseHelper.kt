package helpers.storage.jdbc_wrapping

import logs.Logging
import stConfig
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.SQLSyntaxErrorException

interface DatabaseHelper {

    fun executeQueryWithoutResult(sqlQuery: String)

    fun createTable(sqlQuery: String)

    fun executeQuery(
        sqlQuery: String, resultScope: (
            resultSet: ResultSet,
            isNext: Boolean
        ) -> Unit
    )

    class Base private constructor() : DatabaseHelper {
        private val mDBConnection: Connection

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

        override fun createTable(sqlQuery: String) {
            try {
                mDBConnection.createStatement().apply {
                    execute(sqlQuery)
                }.close()
            } catch (e: SQLSyntaxErrorException) {
                // do nothing
            }
        }

        override fun executeQuery(sqlQuery: String, resultScope: (resultSet: ResultSet, isNext: Boolean) -> Unit) {
            val statement = mDBConnection.createStatement()
            val result: ResultSet
            statement.use { st ->
                result = st.executeQuery(sqlQuery)
                resultScope.invoke(result, result.next())
            }
        }

        object Instance {
            private var mInstance: DatabaseHelper? = null

            fun provideInstance(): DatabaseHelper {
                mInstance = if (mInstance == null) {
                    Base()
                } else {
                    mInstance
                }
                return mInstance ?: throw Exception()
            }
        }
    }
}
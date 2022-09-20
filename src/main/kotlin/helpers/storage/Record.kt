package helpers.storage

import java.sql.ResultSet

abstract class Record() {

    constructor(result: ResultSet) : this()

    abstract fun insertSQLQuery(tableName: String) : String

    abstract fun updateSQLQuery(tableName: String): String

    abstract fun deleteSQLQuery(tableName: String): String
}
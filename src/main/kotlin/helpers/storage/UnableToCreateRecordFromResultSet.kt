package helpers.storage

import java.lang.RuntimeException
import java.sql.ResultSet

class UnableToCreateRecordFromResultSet(
    resultSet: ResultSet,
    tableName: String
) : RuntimeException("Unable to create instance of" +
        " record in table = $tableName, with result set $resultSet")
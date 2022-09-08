package edb_connector_tests

import helpers.storage.EDBConnection
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

//<2>
//#1{userId=1129163878L;codes=[{code=userAge;data=45},{code=deeplinkPageNumber;data=1}]}
//#2{userId=1129163878L;codes=[{code=userAge;data=45},{code=deeplinkPageNumber;data=1}]}

class EDBConnectionTest {

    @Test
    fun readMaxIdTest() {
        val connection = EDBConnection.Base(
            "users.edb"
        )
        assertEquals(2L, connection.maxId())
    }
}
package edb_connector_tests

import helpers.storage.edb_commons.EDBConnection
import kotlin.test.Test
import kotlin.test.assertEquals

class EDBConnectionTest {

    @Test
    fun readMaxIdTest() {
        val connection = EDBConnection.Base(
            "readTest.edb"
        )
        assertEquals(2L, connection.maxId())
    }

    @Test
    fun readRawWithId() {
//        val connection = EDBConnection.Base(
//            "users.edb"
//        )
//        val data = connection.read(1)
//        assertEquals(
//            RawData.Base(1L).apply {
//                put("userId", 1129163878L)
//                put("code", 15.5f)
//            },
//            data
//        )
    }

    @Test
    fun readManyData() {
//        var data : RawData = RawData.Base()
//        SpeedTesting.Base(
//            {
//                val connection = EDBConnection.Base(
//                    "readTest.edb"
//                )
//                data = connection.read(50_000)
//            },
//            "Read from big EDB",
//            400
//        ).test()
//        assertEquals(
//            93438,
//            data.int("userId")
//        )
    }
}
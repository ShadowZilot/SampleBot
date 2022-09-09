package edb_connector_tests

import helpers.storage.EDBConnection
import helpers.storage.RawData
import kotlin.test.Test

class ScriptForInflateEDB {

    @Test
    fun inflateEDB() {
        val connection = EDBConnection.Base(
            "readTest.edb"
        )
        for (i in 1..1_000) {
            connection.insert(
                RawData.Base().apply {
                    put(
                        "userId",
                        (10_000L..99_999L).random()
                    )
                    put(
                        "age",
                        (18..70).random().toFloat()
                    )
                    put(
                        "isPremium",
                        (0..1).random() == 0
                    )
                    put(
                        "email", listOf(
                            "example@gmail.com",
                            "gift@gmail.com",
                            "lake@gmail.com",
                            "pro@gmail.com",
                            "filed@gmail.com",
                            "icq@gmail.com"
                        ).random()
                    )
                }
            )
        }
    }
}
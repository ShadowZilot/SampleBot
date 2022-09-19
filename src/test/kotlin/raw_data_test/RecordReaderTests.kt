package raw_data_test

import SpeedTesting
import helpers.storage.edb_commons.RawData
import helpers.storage.RecordReader
import kotlin.test.Test
import kotlin.test.assertEquals

class RecordReaderTests {

    @Test
    fun baseTest() {
        var raw : RawData = RawData.Base()
        SpeedTesting.Base(
            {
                raw = RecordReader.Base().readRaw(
                    "#2{userId=1129163878L;code=12.5}"
                )
            },
            "Base record reader test",
            1L
        ).test()
        assertEquals(
            RawData.Base(2L).apply {
                put("userId", 1129163878L)
                put("code", 12.5)
            },
            raw
        )
    }
}
package raw_data_test

import admin_bot_functions.statistic.SpeedTesting
import helpers.storage.RawData
import helpers.storage.ReadDataValue
import kotlin.test.Test
import kotlin.test.assertEquals

class ReadDataValueTest {

    @Test
    fun intValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "userAge=18",
                    data
                )
            },
            "Int data value read",
            1L
        ).test()
        assertEquals(
            18,
            data.int("userAge")
        )
    }

    @Test
    fun longValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "userId=2373629619L",
                    data
                )
            },
            "Long data value read",
            1L
        ).test()
        assertEquals(
            2373629619L,
            data.long("userId")
        )
    }

    @Test
    fun floatValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "value=15.45",
                    data
                )
            },
            "Float data value read",
            1L
        ).test()
        assertEquals(
            15.45f,
            data.float("value")
        )
    }

    @Test
    fun stringValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "name=John",
                    data
                )
            },
            "String data value read",
            1
        ).test()
        assertEquals(
            "John",
            data.string("name")
        )
    }

    @Test
    fun booleanValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "isPremium=true",
                    data
                )
            },
            "Boolean data value read",
            1L
        ).test()
        assertEquals(
            true,
            data.boolean("isPremium")
        )
    }

    @Test
    fun arrayValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "codes=[{code=userAge;data=45},{code=deeplinkPageNumber;data=1}]",
                    data
                )
            },
            "Array data value read",
            1L
        ).test()
        assertEquals(
            true,
            data.boolean("isPremium")
        )
    }

    @Test
    fun stringWithPointValue() {
        val data = RawData.Base(1)
        SpeedTesting.Base(
            {
                ReadDataValue.Base().readValue(
                    "sentence=Hello world.",
                    data
                )
            },
            "String data value read",
            1L
        ).test()
        assertEquals(
            "Hello world.",
            data.string("sentence")
        )
    }
}
package raw_data_test

import helpers.storage.RawArray
import helpers.storage.RawData
import kotlin.test.Test
import kotlin.test.assertEquals

class RawDataCacheStringTest {

    @Test
    fun baseTest() {
        val data = RawData.Base(
            1
        ).apply {
            put("userId", 1129163878L)
            put("codes", RawArray.Base().apply {
                put(RawData.Base().apply {
                    put("code", "userAge")
                    put("data", 45)
                })
                put(RawData.Base().apply {
                    put("code", "deeplinkPageNumber")
                    put("data", 1)
                })
            })
        }
        val actualCache = data.cachedRecord()
        assertEquals(
            "#1{userId=1129163878L;codes=[{code=userAge;data=45},{code=deeplinkPageNumber;data=1}]}",
            actualCache
        )
    }

    @Test
    fun floatDataTest() {
        val data = RawData.Base(
            1
        ).apply {
            put("userId", 1129163878L)
            put("codes", RawArray.Base().apply {
                put(RawData.Base().apply {
                    put("code", "userAge")
                    put("data", 45.5)
                })
            })
        }
        val actualCache = data.cachedRecord()
        assertEquals(
            "#1{userId=1129163878L;codes=[{code=userAge;data=45.5}]}",
            actualCache
        )
    }

    @Test
    fun booleanDataTest() {
        val data = RawData.Base(
            1
        ).apply {
            put("userId", 1129163878L)
            put("codes", RawArray.Base().apply {
                put(RawData.Base().apply {
                    put("code", "userAge")
                    put("data", 45.5)
                })
            })
            put("isPremium", true)
        }
        val actualCache = data.cachedRecord()
        assertEquals(
            "#1{userId=1129163878L;codes=[{code=userAge;data=45.5}];isPremium=true}",
            actualCache
        )
    }
}
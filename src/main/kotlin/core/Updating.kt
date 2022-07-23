package core

import org.json.JSONObject

data class Updating(
    private val mUpdate: JSONObject = JSONObject()
) {
    fun <T> map(
        mapper: Mapper<T>
    ): T {
        return mapper.map(mUpdate)
    }

    interface Mapper<T> {
        fun map(
            updating: JSONObject
        ): T
    }
}


package core

import org.json.JSONException
import org.json.JSONObject

interface UpdatingByResponse {

    fun updates(): List<Updating>

    fun updatingId(): Long

    class Base(
        private val mResponse: String
    ) : UpdatingByResponse {

        override fun updates(): List<Updating> {
            val result = mutableListOf<Updating>()
            return try {
                val responseJson = JSONObject(mResponse)
                if (responseJson.getBoolean("ok")) {
                    val array = responseJson.getJSONArray("result")
                    for (i in 0 until array.length()) {
                        result.add(
                            Updating(
                                array.getJSONObject(i)
                            )
                        )
                    }
                    result
                } else {
                    result
                }
            } catch (e: JSONException) {
                result
            }
        }

        override fun updatingId(): Long {
            return try {
                val array = JSONObject(mResponse)
                    .getJSONArray("result")
                array
                    .getJSONObject(array.length() - 1)
                    .getLong("update_id")
            } catch (e: JSONException) {
                0L
            }
        }
    }
}
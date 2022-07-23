package helpers.storage

import org.json.JSONArray
import org.json.JSONObject
import stating.State

class StatesFileHelping(
    file: JsonFile,
    private val mCache: State.Mapper<JSONObject>
) : StorageHandling<State>(file) {

    override fun load() = mutableListOf<State>().apply {
        val data = mFile.array()
        for (i in 0 until data.length()) {
            add(
                State(data.getJSONObject(i))
            )
        }
    }

    override fun cache(data: List<State>) {
        mFile.writeArray(
            JSONArray().apply {
                data.forEach {
                    put(it.map(mCache))
                }
            }
        )
    }
}
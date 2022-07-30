package helpers.storage

import org.json.JSONArray
import staging.State

class StatesFileHelping(
    file: JsonFile,
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
                    put(it.toJson())
                }
            }
        )
    }
}
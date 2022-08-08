package admin_bot_functions.deeplinking.storage

import helpers.storage.JsonFile
import helpers.storage.StorageHandling
import org.json.JSONArray
import org.json.JSONObject

class DeeplinkFileHandling(
    file: JsonFile,
    private val mCache: Deeplink.Mapper<JSONObject>
) : StorageHandling<Deeplink>(file) {

    override fun load() = mutableListOf<Deeplink>().apply {
        val data = mFile.array()
        for (i in 0 until data.length()) {
            add(
                Deeplink(data.getJSONObject(i))
            )
        }
    }

    override fun cache(data: List<Deeplink>) {
        mFile.writeArray(
            JSONArray().apply {
                data.forEach {
                    put(it.map(mCache))
                }
            }
        )
    }
}
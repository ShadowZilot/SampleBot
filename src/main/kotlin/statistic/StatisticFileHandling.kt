package statistic

import helpers.storage.JsonFile
import helpers.storage.StorageHandling
import org.json.JSONArray
import org.json.JSONObject

class StatisticFileHandling(
    file: JsonFile,
    private val mCache: StatisticItem.Mapper<JSONObject>
    ) : StorageHandling<StatisticItem>(file) {

    override fun load() = mutableListOf<StatisticItem>().apply {
        val data = mFile.array()
        for (i in 0 until data.length()) {
            add(
                StatisticItem(data.getJSONObject(i))
            )
        }
    }

    override fun cache(data: List<StatisticItem>) {
        mFile.writeArray(
            JSONArray().apply {
                data.forEach {
                    put(it.map(mCache))
                }
            }
        )
    }
}
package admin_bot_functions.admins_storage

import helpers.storage.JsonFile
import helpers.storage.StorageHandling
import org.json.JSONArray
import org.json.JSONObject

class AdminsFileHandling(
    file: JsonFile,
    private val mCache: Admin.Mapper<JSONObject>
) : StorageHandling<Admin>(file) {

    override fun load() = mutableListOf<Admin>().apply {
        val data = mFile.array()
        for (i in 0 until data.length()) {
            add(
                Admin(data.getJSONObject(i))
            )
        }
    }

    override fun cache(data: List<Admin>) {
        mFile.writeArray(
            JSONArray().apply {
                data.forEach {
                    put(it.map(mCache))
                }
            }
        )
    }
}
package helpers.storage

import users.User
import org.json.JSONArray
import org.json.JSONObject

class UserFileHelping(
    file: JsonFile,
    private val mCache: User.Mapper<JSONObject>
) : StorageHandling<User>(file) {

    override fun load() = mutableListOf<User>().apply {
        val data = mFile.array()
        for (i in 0 until data.length()) {
            add(
                User(data.getJSONObject(i))
            )
        }
    }

    override fun cache(data: List<User>) {
        mFile.writeArray(
            JSONArray().apply {
                data.forEach {
                    put(it.map(mCache))
                }
            }
        )
    }
}
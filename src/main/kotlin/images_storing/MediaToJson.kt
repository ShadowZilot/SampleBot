package images_storing

import org.json.JSONArray
import org.json.JSONObject

class MediaToJson : MediaItem.Mapper<JSONArray> {

    override fun map(media: List<Pair<String, String>>) = JSONArray().apply {
        media.forEach {
            put(
                JSONObject().apply {
                    put("type", it.first)
                    put("id", it.second)
                }
            )
        }
    }
}
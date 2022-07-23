package images_storing

import org.json.JSONArray
import org.json.JSONObject

class MediaToRequestToPart(
    private val mCaption: String
) : MediaItem.Mapper<JSONArray> {

    override fun map(media: List<Pair<String, String>>): JSONArray {
        val array = JSONArray()
        for (i in media.indices) {
            val mediaItem = JSONObject()
            mediaItem.put("type", media[i].first)
            mediaItem.put("media", media[i].second)
            if (i == 0) {
                mediaItem.put("caption", mCaption)
                mediaItem.put("parse_mode", "MarkdownV2")
            }
            array.put(mediaItem)
        }
        return array
    }
}
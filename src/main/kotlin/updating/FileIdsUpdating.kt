package updating

import core.Updating
import images_storing.MediaItem
import images_storing.MediaToJson
import org.json.JSONException
import org.json.JSONObject

class FileIdsUpdating : Updating.Mapper<MediaItem> {

    override fun map(updating: JSONObject): MediaItem {
        return try {
            MediaItem(
                updating.getJSONObject("message")
                    .getJSONArray("photo")
            )
        } catch (e: JSONException) {
            MediaItem(emptyList())
        }
    }
}
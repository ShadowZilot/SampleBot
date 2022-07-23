package updating

import core.Updating
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ChangePhotosInUpdating(
    private val mPhotos: JSONArray
) : Updating.Mapper<Updating> {

    override fun map(updating: JSONObject): Updating {
        return try {
            updating.getJSONObject("message").remove("photo")
            updating.getJSONObject("message").put("photo", mPhotos)
            Updating(updating)
        } catch (e: JSONException) {
            Updating(updating)
        }
    }
}
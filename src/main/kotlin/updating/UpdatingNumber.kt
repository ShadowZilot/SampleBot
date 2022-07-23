package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject
import java.lang.NumberFormatException

class UpdatingNumber : Updating.Mapper<Long> {

    override fun map(updating: JSONObject): Long {
        return try {
            updating.getJSONObject("message")
                .getString("text").toLong()
        } catch (e: JSONException) {
            0L
        }
        catch (e: NumberFormatException) {
            0L
        }
    }
}
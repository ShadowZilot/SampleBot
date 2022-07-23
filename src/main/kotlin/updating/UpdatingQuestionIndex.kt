package updating

import core.Updating
import org.json.JSONException
import org.json.JSONObject
import java.lang.NumberFormatException

class UpdatingQuestionIndex : Updating.Mapper<Int> {

    override fun map(updating: JSONObject): Int {
        return try {
            val data = updating.getJSONObject("callback_query")
                .getString("data").split("=")
            data[1].toInt()
        } catch (e: JSONException) {
            -1
        } catch (e: NumberFormatException) {
            -1
        }
    }
}
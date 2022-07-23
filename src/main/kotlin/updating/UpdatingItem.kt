package updating

import core.Updating
import org.json.JSONObject

class UpdatingItem : Updating.Mapper<JSONObject> {
    override fun map(updating: JSONObject) = updating
}

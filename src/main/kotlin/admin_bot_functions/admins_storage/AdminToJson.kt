package admin_bot_functions.admins_storage

import org.json.JSONObject

class AdminToJson : Admin.Mapper<JSONObject> {

    override fun map(userId: Long) = JSONObject().apply {
        put("userId", userId)
    }
}
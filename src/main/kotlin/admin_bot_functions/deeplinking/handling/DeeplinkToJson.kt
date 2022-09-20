package admin_bot_functions.deeplinking.handling

import admin_bot_functions.deeplinking.storage.Deeplink
import org.json.JSONObject

class DeeplinkToJson : Deeplink.Mapper<JSONObject> {

    override fun map(
        id: Int,
        name: String,
        code: String,
        link: String,
        countUsers: Int
    ) = JSONObject().apply {
        put("name", name)
        put("code", code)
        put("link", link)
        put("usersCount", countUsers)
    }
}
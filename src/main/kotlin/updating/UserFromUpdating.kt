package updating

import users.User
import core.Updating
import helpers.safetyString
import org.json.JSONException
import org.json.JSONObject

class UserFromUpdating : Updating.Mapper<User> {

    override fun map(updating: JSONObject): User {
        return try {
            val from = if (updating.has("callback_query")) {
                updating
                    .getJSONObject("callback_query")
                    .getJSONObject("from")
            } else {
                updating
                    .getJSONObject("message")
                    .getJSONObject("from")
            }
            User(
                from.getLong("id"),
                from.safetyString("username"),
                from.safetyString("first_name"),
                from.safetyString("last_name")
            )
        } catch (e: JSONException) {
            User(
                0L,
                "",
                "",
                ""
            )
        }
    }
}
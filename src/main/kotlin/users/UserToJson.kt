package users

import org.json.JSONObject

class UserToJson : User.Mapper<JSONObject> {

    override fun map(
        id: Long,
        username: String,
        firstName: String,
        secondName: String,
        isStarted: Boolean
    ) = JSONObject().apply {
        put("id", id)
        put("username", username)
        put("firstName", firstName)
        put("secondName", secondName)
        put("isStarted", isStarted)
    }
}
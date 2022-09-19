package admin_bot_functions.admins_storage

import core.Updating
import helpers.storage.Record
import org.json.JSONObject
import updating.UpdatingChatId

data class Admin(
    private val mUserId: Long
) : Record() {
    constructor(updating: Updating) : this(
        updating.map(UpdatingChatId()).second
    )

    constructor(item: JSONObject) : this(
        item.getLong("userId")
    )

    fun <T> map(mapper: Mapper<T>) : T = mapper.map(
        mUserId
    )

    interface Mapper<T> {
        fun map(
            userId: Long
        ) : T
    }
}
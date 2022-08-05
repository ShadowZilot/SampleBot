package admin_bot_functions.admins_storage

import core.Updating
import updating.UpdatingChatId

class AdminSameId(
    private val mUpdating: Updating
) : Admin.Mapper<Boolean> {
    override fun map(userId: Long) = userId == mUpdating.map(UpdatingChatId()).second
}
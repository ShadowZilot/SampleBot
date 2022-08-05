package admin_bot_functions.base

import admin_bot_functions.admins_storage.AdminsHandling
import core.Updating
import org.json.JSONObject

class IsUserAdmin(
    private val mAdmins: AdminsHandling
) : Updating.Mapper<Boolean> {

    override fun map(updating: JSONObject) = mAdmins.isUserAdmin(Updating(updating))
}
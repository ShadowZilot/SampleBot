package admin_bot_functions.admins_storage

import core.Updating
import helpers.storage.StorageHandling

interface AdminsHandling {

    fun addNewAdmin(updating: Updating)

    fun isUserAdmin(updating: Updating): Boolean

    class Base(
        private val mStore: StorageHandling<Admin>
    ) : AdminsHandling {
        private val mAdmins = mStore.load()

        override fun addNewAdmin(updating: Updating) {
            mAdmins.add(Admin(updating))
            mStore.cache(mAdmins)
        }

        override fun isUserAdmin(updating: Updating): Boolean {
            val result = mAdmins.find {
                it.map(AdminSameId(updating))
            }
            return result != null
        }
    }
}
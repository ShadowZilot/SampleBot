package admin_bot_functions.admins_storage

import helpers.storage.EDBConnection
import helpers.storage.StorageHandling
import org.json.JSONObject

class AdminsFileHandling(
    file: EDBConnection,
    private val mCache: Admin.Mapper<JSONObject>
) : StorageHandling<Admin>(file) {

    override fun insert(data: Admin) {
        TODO("Not yet implemented")
    }

    override fun update(data: Admin) {
        TODO("Not yet implemented")
    }

    override fun delete(data: Admin) {
        TODO("Not yet implemented")
    }

    override fun read(): Admin {
        TODO("Not yet implemented")
    }

    override fun readNext(): Admin {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}
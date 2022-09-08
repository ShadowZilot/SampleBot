package admin_bot_functions.deeplinking.storage

import helpers.storage.EDBConnection
import helpers.storage.StorageHandling
import org.json.JSONObject

class DeeplinkFileHandling(
    file: EDBConnection,
    private val mCache: Deeplink.Mapper<JSONObject>
) : StorageHandling<Deeplink>(file) {

    override fun insert(data: Deeplink) {
        TODO("Not yet implemented")
    }

    override fun update(data: Deeplink) {
        TODO("Not yet implemented")
    }

    override fun delete(data: Deeplink) {
        TODO("Not yet implemented")
    }

    override fun read(): Deeplink {
        TODO("Not yet implemented")
    }

    override fun readNext(): Deeplink {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}
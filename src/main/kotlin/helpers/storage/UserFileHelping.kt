package helpers.storage

import users.User
import org.json.JSONObject

class UserFileHelping(
    file: EDBConnection,
    private val mCache: User.Mapper<JSONObject>
) : StorageHandling<User>(file) {
    override fun insert(data: User) {
        TODO("Not yet implemented")
    }

    override fun update(data: User) {
        TODO("Not yet implemented")
    }

    override fun delete(data: User) {
        TODO("Not yet implemented")
    }

    override fun read(): User {
        TODO("Not yet implemented")
    }

    override fun readNext(): User {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

}
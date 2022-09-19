package helpers.storage

import helpers.storage.edb_commons.EDBConnection
import staging.State

class StatesFileHelping(
    file: EDBConnection,
) : StorageHandling<State>(file) {

    override fun insert(data: State) {
        TODO("Not yet implemented")
    }

    override fun update(data: State) {
        TODO("Not yet implemented")
    }

    override fun delete(data: State) {
        TODO("Not yet implemented")
    }

    override fun read(): State {
        TODO("Not yet implemented")
    }

    override fun readNext(): State {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}
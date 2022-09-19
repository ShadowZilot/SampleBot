package helpers.storage

import helpers.storage.edb_commons.EDBConnection

abstract class StorageHandling<T : Record> (
    protected val mFile: EDBConnection
)  {

    abstract fun insert(data: T)

    abstract fun update(data: T)

    abstract fun delete(data: T)

    abstract fun read(): T

    abstract fun readNext(): T

    abstract fun hasNext(): Boolean

    abstract fun reset()
}
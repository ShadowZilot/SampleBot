package helpers.storage

interface StorageShell {

    fun tableSchema() : String

    fun tableName() : String
}
package helpers.storage

import org.json.JSONArray

abstract class StorageHandling<T> (
    protected val mFile: JsonFile
)  {
    abstract fun load(): MutableList<T>

    abstract fun cache(data: List<T>)

    fun cache(data: JSONArray) {
        mFile.writeArray(data)
    }
}
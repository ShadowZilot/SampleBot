package helpers.storage

import org.json.JSONArray
import java.io.File

interface JsonFile {

    fun array(): JSONArray

    fun writeArray(cachingArray: JSONArray)

    class Base(
        private val mFile: File
    ) : JsonFile {

        init {
            if (!mFile.exists()) {
                mFile.createNewFile()
            }
        }

        override fun array(): JSONArray {
            val text = mFile.readText()
            return if (text.isNotEmpty()) {
                JSONArray(text)
            } else {
                JSONArray()
            }
        }

        override fun writeArray(cachingArray: JSONArray) {
            mFile.writeText(
                cachingArray.toString(2)
            )
        }
    }
}
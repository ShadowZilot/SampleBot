package configuration

import org.json.JSONException
import org.json.JSONObject
import java.io.File

interface Config {

    fun configValueString(key: String): String

    fun configValueLong(key: String): Long

    class Base(
        file: File
    ) : Config {
        private val mConfiguration = JSONObject(
            file.readText()
        )

        override fun configValueString(key: String): String = try {
            mConfiguration.getString(key)
        } catch (e: JSONException) {
            throw ConfigValueNotFound(key)
        }

        override fun configValueLong(key: String): Long = try {
            mConfiguration.getLong(key)
        } catch (e: JSONException) {
            throw ConfigValueNotFound(key)
        }
    }
}
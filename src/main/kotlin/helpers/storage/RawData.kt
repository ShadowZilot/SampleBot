package helpers.storage

interface RawData {

    fun put(key: String, data: Any)

    fun string(key: String): String

    fun array(key: String): RawArray

    fun int(key: String): Int

    fun long(key: String): Long

    fun float(key: String): Float

    fun boolean(key: String): Boolean

    fun cachedRecord(): String

    class Base : RawData {
        private var mId: Long = -1L
        private val mValues = mutableMapOf<String, Any>()

        override fun put(key: String, data: Any) {
            mValues[key] = data
        }

        override fun string(key: String) : String = if (mValues[key] is String) {
            mValues[key] as String
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun array(key: String): RawArray = if (mValues[key] is RawArray) {
            mValues[key] as RawArray
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun int(key: String): Int = if (mValues[key] is Int) {
            mValues[key] as Int
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun long(key: String): Long = if (mValues[key] is Long) {
            mValues[key] as Long
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun float(key: String): Float = if (mValues[key] is Float) {
            mValues[key] as Float
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun boolean(key: String): Boolean = if (mValues[key] is Boolean) {
            mValues[key] as Boolean
        } else {
            throw RawDataNotFoundException(key)
        }

        override fun cachedRecord() : String {
            return ""
        }
    }
}
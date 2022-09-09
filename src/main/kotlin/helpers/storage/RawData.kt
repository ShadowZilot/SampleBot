package helpers.storage

private const val mOpenRecordSymbol = '#'
private const val mSeparatorSymbol = ';'
private const val mValueSymbol = '='
private const val mArrayItemOpenSymbol = '{'
private const val mArrayItemEndSymbol = '}'

interface RawData {

    fun put(key: String, data: Any)

    fun string(key: String): String

    fun array(key: String): RawArray

    fun int(key: String): Int

    fun long(key: String): Long

    fun float(key: String): Float

    fun boolean(key: String): Boolean

    fun cachedRecord(id: Long): String

    fun id(): Long

    class Base(
        private var mId: Long = -1L
    ) : RawData {
        private val mValues = mutableMapOf<String, Any>()

        override fun put(key: String, data: Any) {
            mValues[key] = data
        }

        override fun string(key: String): String = if (mValues[key] is String) {
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

        override fun cachedRecord(id: Long) = buildString {
            if (mId > 0L) {
                append(mOpenRecordSymbol)
                append(mId)
            }
            append(mArrayItemOpenSymbol)
            for (key in mValues.keys) {
                append(key)
                append(mValueSymbol)
                if (mValues[key] is RawArray) {
                    append((mValues[key] as RawArray).stringSource())
                } else {
                    append(mValues[key])
                }
                if (mValues[key] is Long) {
                    append('L')
                }
                if (key != mValues.keys.last()) {
                    append(mSeparatorSymbol)
                }
            }
            append(mArrayItemEndSymbol)
        }

        override fun id() = mId

        override fun toString() = cachedRecord(mId)

        override fun hashCode(): Int {
            var summa = 0
            for (data in mValues) {
                summa += data.value.hashCode()
            }
            return summa
        }

        override fun equals(other: Any?): Boolean {
            return if (other != null && other is RawData) {
                other.cachedRecord(other.id()) == cachedRecord(mId)
            } else {
                false
            }
        }
    }
}
package core

import java.io.File
import java.lang.Exception
import java.lang.NumberFormatException

interface MaxUpdatingId {
    fun value(): Long

    fun postValue(newValue: Long)

    class Base(
        private val mStore: File
    ) : MaxUpdatingId {
        init {
            if (!mStore.isFile || !mStore.exists()) {
                throw Exception()
            }
        }

        override fun value(): Long {
            return try {
                mStore.readText().toLong()
            } catch (e: NumberFormatException) {
                0L
            }
        }

        override fun postValue(newValue: Long) {
            mStore.writeText(
                newValue.toString()
            )
        }
    }
}
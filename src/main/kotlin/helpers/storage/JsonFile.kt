package helpers.storage

import java.io.*

interface JsonFile {

    fun insert(data: Record)

    fun update(data: Record)

    fun delete(data: Record)

    fun read(): Record

    fun readNext(): Record

    fun hasNext(): Boolean

    fun reset()

    class Base(
        private val mFile: File
    ) : JsonFile {
        private val mReader = BufferedReader(
            InputStreamReader(
                FileInputStream(mFile), "UTF-8"
            )
        )

        private val mWriter = BufferedWriter(
            OutputStreamWriter(
                FileOutputStream(mFile), "UTF-8"
            )
        )

        init {
            if (!mFile.exists()) {
                mFile.createNewFile()
            }
        }

        override fun insert(data: Record) {
            mWriter
                .append(data.toData().toString(2))
                .close()
        }

        override fun update(data: Record) {

        }

        override fun delete(data: Record) {

        }

        override fun read(): Record {
            throw Exception()
        }

        override fun readNext(): Record {
            throw Exception()
        }

        override fun hasNext(): Boolean {
            return true
        }

        override fun reset() {

        }
    }
}
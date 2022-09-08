package helpers.storage

import sBasePath
import java.io.*

private const val mOpenIdSymbol = '<'
private const val mCloseIdSymbol = '>'

interface EDBConnection {

    fun insert(data: RawData)

    fun update(data: RawData)

    fun delete(data: RawData)

    fun read(id: Long): RawData

    fun readNext(): RawData

    fun hasNext(): Boolean

    fun reset()

    fun maxId() : Long

    class Base(
        fileName: String
    ) : EDBConnection {
        private val mReader = BufferedReader(
            FileReader("$sBasePath$fileName")
        )
        private val mWriter = BufferedWriter(
            FileWriter("$sBasePath$fileName", true)
        )
        private val mMaxId : Long by lazy {
            val firstLine = mReader.readLine()
            if (firstLine[0] == mOpenIdSymbol) {
                val endIndex = firstLine.indexOf(mCloseIdSymbol)
                firstLine.substring(
                    1 until endIndex
                ).toLong()
            } else {
                0L
            }
        }

        override fun insert(data: RawData) {
            mWriter
                .append(data.cachedRecord())
                .close()
        }

        override fun update(data: RawData) {

        }

        override fun delete(data: RawData) {

        }

        override fun read(id: Long): RawData {
            throw Exception()
        }

        override fun readNext(): RawData {
            throw Exception()
        }

        override fun hasNext(): Boolean {
            return true
        }

        override fun reset() {
            mReader.reset()
        }

        override fun maxId() = mMaxId
    }
}

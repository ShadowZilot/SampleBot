package helpers.storage.edb_commons

import helpers.storage.RecordReader
import helpers.storage.exceptions.RawDataIsUp
import helpers.storage.exceptions.RawDataNotFound
import logs.Logging
import sBasePath
import java.io.BufferedWriter
import java.io.FileWriter
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.StandardOpenOption
import kotlin.io.path.Path

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

    fun maxId(): Long

    class Base(
        private val fileName: String
    ) : EDBConnection {
        private val mIOChannel = FileChannel.open(
            Path("$sBasePath$fileName"),
            StandardOpenOption.READ,
            StandardOpenOption.WRITE,
        )
        private val mBuffer = ByteBuffer.allocateDirect(200)
        private val mRecordReader = RecordReader.Base()
        private var mCurrentLine: String? = null
        private var mMaxId: Long = 0L
        init {
            // read id
            val readByte = mIOChannel.read(mBuffer)
            val id = StringBuilder()
            if (readByte > 0) {
                if (mBuffer[0].toInt().toChar() == mOpenIdSymbol) {
                    var index = 1
                    var currentChar = mBuffer[index].toInt().toChar()
                    while (currentChar != mCloseIdSymbol && index < mBuffer.limit()) {
                        id.append(currentChar)
                        index++
                        currentChar = mBuffer[index].toInt().toChar()
                    }
                }
            }
            Logging.ConsoleLog.log(id.toString())
            mMaxId = id.toString().toLong()
            // write id
            mBuffer.clear()
            val idBytes = ByteBuffer.wrap(
                "<34>".encodeToByteArray()
            )
            mIOChannel.write(
                idBytes,
                3
            )
            mIOChannel.close()
        }

        override fun insert(data: RawData) {
            val idWriter = BufferedWriter(
                FileWriter("$sBasePath$fileName")
            )
            mMaxId++
            idWriter.write(
                "$mOpenIdSymbol$mMaxId$mCloseIdSymbol\n",
                0,
                3 + mMaxId.toString().length
            )
            idWriter.close()
            val commonWriter = BufferedWriter(
                FileWriter("$sBasePath$fileName", true)
            )
            commonWriter
                .append("${data.cachedRecord(mMaxId)}\n")
                .close()
        }

        override fun update(data: RawData) {

        }

        override fun delete(data: RawData) {

        }

        override fun read(id: Long): RawData {
            throw RawDataNotFound(id)
        }

        override fun readNext(): RawData {
            throw RawDataIsUp()
        }

        override fun hasNext(): Boolean {
            return false
        }

        override fun reset() {
        }

        override fun maxId() = mMaxId
    }
}

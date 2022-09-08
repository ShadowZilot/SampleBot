package helpers.storage

import helpers.storage.exceptions.NoDataIdFound


private const val mOpenRecordSymbol = '#'
private const val mArrayItemOpenSymbol = '{'

interface RecordReader {

    fun readRaw(rawString: String): RawData

    class Base : RecordReader {
        override fun readRaw(rawString: String): RawData {
            val startRecord: Int
            val id = if (rawString[0] == mOpenRecordSymbol) {
                startRecord = rawString.indexOf(mArrayItemOpenSymbol, 0)
                rawString.substring(
                    0 until startRecord
                ).replace(mOpenRecordSymbol.toString(), "").toLong()
            } else {
                throw NoDataIdFound(rawString)
            }
            return RawDataReader.Base().readRawData(
                id,
                rawString.substring(
                    startRecord,
                    rawString.length
                )
            )
        }
    }
}
package helpers.storage

import helpers.storage.exceptions.NoDataIdFound


private const val mOpenRecordSymbol = '#'
private const val mArrayItemOpenSymbol = '{'
private const val mOpenIdSymbol = '<'

interface RecordReader {

    fun readRaw(rawString: String): RawData

    fun readId(rawString: String): Long

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

        override fun readId(rawString: String): Long {
            val startRecord: Int
            return if (rawString[0] == mOpenRecordSymbol) {
                startRecord = rawString.indexOf(mArrayItemOpenSymbol, 0)
                rawString.substring(
                    0 until startRecord
                ).replace(mOpenRecordSymbol.toString(), "").toLong()
            } else if (rawString[0] == mOpenIdSymbol) {
                -1L
            } else {
                throw NoDataIdFound(rawString)
            }
        }
    }
}
package helpers.storage.edb_commons

import helpers.storage.exceptions.DataRecordNotRecognized

private const val mSeparatorSymbol = ';'
private const val mArrayItemOpenSymbol = '{'
private const val mArrayItemEndSymbol = '}'

interface RawDataReader {

    fun readRawData(id: Long = -1L, source: String): RawData

    class Base : RawDataReader {
        override fun readRawData(id: Long, source: String): RawData {
            val valueReader = ReadDataValue.Base()
            val result = if (id != -1L) {
                RawData.Base(id)
            } else {
                RawData.Base()
            }
            val outSource = if (source.first() == mArrayItemOpenSymbol && source.last() == mArrayItemEndSymbol) {
                source
                    .removePrefix(mArrayItemOpenSymbol.toString())
                    .removeSuffix(mArrayItemEndSymbol.toString())
            } else {
                throw DataRecordNotRecognized(source)
            }
            val values = outSource.split(mSeparatorSymbol)
            for (i in values.indices) {
                valueReader.readValue(
                    values[i],
                    result
                )
            }
            return result
        }
    }
}
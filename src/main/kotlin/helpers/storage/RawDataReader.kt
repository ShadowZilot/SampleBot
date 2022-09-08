package helpers.storage

import helpers.storage.exceptions.NoDataIdFound


private const val mOpenRecordSymbol = '#'
private const val mSeparatorSymbol = ';'
private const val mValueSymbol = '='
private const val mArrayItemOpenSymbol = '{'
private const val mArrayItemEndSymbol = '}'
private const val mArrayStartSymbol = '['
private const val mArrayEndSymbol = ']'
private const val mArrayItemDividerSymbol = ','

interface RawDataReader {

    fun readRaw(rawString: String): RawData

    class Base : RawDataReader {
        // #1{userId=1129163878L;codes=[{code=userAge;data=45},{code=floatNumber;data=1.5}];isAwait=true}
        override fun readRaw(rawString: String): RawData {
            val startRecord : Int
            val result = if (rawString[0] == mOpenRecordSymbol) {
                startRecord = rawString.indexOf(mArrayItemOpenSymbol, 0)
                RawData.Base(
                    rawString.substring(
                        0..startRecord
                    ).toLong()
                )
            } else {
                throw NoDataIdFound(rawString)
            }
            for (i in (startRecord + 1) until rawString.length) {

            }
            return result
        }
    }
}
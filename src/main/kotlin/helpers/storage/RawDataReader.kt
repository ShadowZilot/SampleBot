package helpers.storage

interface RawDataReader {

    fun readRaw(rawString: String): RawData

    class Base(
        private val mOpenIdSymbol: Char,
        private val mCloseIdSymbol: Char,
        private val mOpenRecordSymbol: Char,
        private val mSeparatorSymbol: Char,
        private val mValueSymbol: Char,
        private val mArrayStartSymbol: Char,
        private val mArrayEndSymbol: Char,
        private val mArrayItemOpenSymbol: Char,
        private val mArrayItemEndSymbol: Char,
        private val mArrayItemDividerSymbol: Char
    ) : RawDataReader {

        override fun readRaw(rawString: String): RawData {
            return RawData.Base()
        }
    }
}
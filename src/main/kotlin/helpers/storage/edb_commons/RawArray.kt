package helpers.storage.edb_commons

private const val mArrayStartSymbol = '['
private const val mArrayEndSymbol = ']'
private const val mArrayItemDividerSymbol = ','

interface RawArray {

    fun length(): Int

    fun itemAt(index: Int): RawData

    fun put(item: RawData, index: Int = -1)

    fun stringSource(): String

    class Base : RawArray {
        private val mValues = mutableListOf<RawData>()

        override fun length() = mValues.size

        override fun itemAt(index: Int) = mValues[index]


        override fun put(item: RawData, index: Int) {
            if (index == -1) {
                mValues.add(item)
            } else {
                mValues.add(index, item)
            }
        }

        override fun stringSource() = buildString {
            append(mArrayStartSymbol)
            for (i in mValues.indices) {
                append(mValues[i].cachedRecord(-1L))
                if (i != mValues.size - 1) {
                    append(mArrayItemDividerSymbol)
                }
            }
            append(mArrayEndSymbol)
        }
    }
}
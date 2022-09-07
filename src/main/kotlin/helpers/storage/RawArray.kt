package helpers.storage

interface RawArray {

    fun length() : Int

    fun itemAt(index: Int) : RawData

    fun put(item: RawData, index: Int = -1)

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
    }
}
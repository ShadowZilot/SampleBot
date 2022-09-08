package helpers.storage

import helpers.storage.exceptions.UnexpectedSymbolInData

private const val mValueSymbol = '='

interface ReadDataValue {

    fun readValue(valueView: String, data: RawData)

    class Base : ReadDataValue {
        override fun readValue(valueView: String, data: RawData) {
            val keyAndValue = valueView.split(mValueSymbol)
            if (keyAndValue.size == 2) {
                val stringPair = Pair(
                    keyAndValue[0],
                    keyAndValue[1]
                )
                val value: Any = if (stringPair.second.contains("L")) {
                    stringPair
                        .second
                        .replace("L", "")
                        .toLong()
                } else if (stringPair.second.contains(".")) {
                    try {
                        stringPair.second.toFloat()
                    } catch (e: NumberFormatException) {
                        stringPair.second
                    }
                } else if (stringPair.second.contains("true")
                    || stringPair.second.contains("false")
                ) {
                    stringPair.second.toBoolean()
                } else {
                    try {
                        stringPair.second.toInt()
                    } catch (e: NumberFormatException) {
                        stringPair.second
                    }
                }
                data.put(
                    stringPair.first,
                    value
                )
            } else {
                throw UnexpectedSymbolInData(
                    mValueSymbol,
                    valueView
                )
            }
        }
    }
}
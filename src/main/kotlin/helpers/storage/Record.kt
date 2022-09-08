package helpers.storage

import org.json.JSONObject

abstract class Record () {

    constructor(item: RawData) : this()

    abstract fun id() : Long

    abstract fun toData() : JSONObject

    abstract fun contentLength(): Int
}
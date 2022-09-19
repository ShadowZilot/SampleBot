package helpers.storage

import java.sql.ResultSet

abstract class Record() {

    constructor(result: ResultSet) : this()
}
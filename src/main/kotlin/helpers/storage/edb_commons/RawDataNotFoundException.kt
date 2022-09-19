package helpers.storage.edb_commons

import java.lang.RuntimeException

class RawDataNotFoundException(
    key: String
) : RuntimeException("RawData value with key = $key not found")
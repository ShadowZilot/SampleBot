package helpers.storage

import java.lang.RuntimeException

class RawDataNotFoundException(
    key: String
) : RuntimeException("RawData value with key = $key not found")
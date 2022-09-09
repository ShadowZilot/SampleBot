package helpers.storage.exceptions

import java.lang.RuntimeException

class RawDataNotFound(
    id: Long
) : RuntimeException("Raw data with id = $id not found")
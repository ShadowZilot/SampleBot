package helpers.storage.exceptions

import java.lang.RuntimeException

class NoDataIdFound(
    input: String
) : RuntimeException("RawDataReader can't find id in record = $input")
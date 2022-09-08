package helpers.storage.exceptions

import java.lang.RuntimeException

class DataRecordNotRecognized(
    record: String
) : RuntimeException("Record = $record not recognized to raw data")
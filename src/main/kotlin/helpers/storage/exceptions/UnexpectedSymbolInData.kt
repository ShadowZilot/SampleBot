package helpers.storage.exceptions

import java.lang.RuntimeException

class UnexpectedSymbolInData(
    symbol: Char,
    source: String
) : RuntimeException(
    "Data reader found unexpected symbol $symbol in source string $source"
)
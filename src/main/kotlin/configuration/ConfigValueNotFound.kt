package configuration

import java.lang.RuntimeException

class ConfigValueNotFound(
    val key: String
) : RuntimeException("Config value with key = $key not found!")
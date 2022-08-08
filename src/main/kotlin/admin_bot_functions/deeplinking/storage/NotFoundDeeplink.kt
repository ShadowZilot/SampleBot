package admin_bot_functions.deeplinking.storage

import java.lang.RuntimeException

class NotFoundDeeplink(code: String) : RuntimeException(
    "Deeplink with code = $code not found!"
)
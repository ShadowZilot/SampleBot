package admin_bot_functions.deeplinking.handling

import admin_bot_functions.deeplinking.storage.Deeplink

class PlusUserToDeeplink : Deeplink.Mapper<Deeplink> {
    override fun map(id: Int, name: String, code: String, link: String, countUsers: Int) = Deeplink(
        id,
        name, code, link, countUsers + 1
    )
}
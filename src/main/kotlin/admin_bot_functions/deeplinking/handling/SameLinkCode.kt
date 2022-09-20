package admin_bot_functions.deeplinking.handling

import admin_bot_functions.deeplinking.storage.Deeplink

class SameLinkCode(
    private val mGeneratedCode: String
) : Deeplink.Mapper<Boolean> {
    override fun map(id: Int, name: String, code: String, link: String, countUsers: Int) = code == mGeneratedCode
}
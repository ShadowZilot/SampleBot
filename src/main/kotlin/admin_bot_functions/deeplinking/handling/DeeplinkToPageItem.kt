package admin_bot_functions.deeplinking.handling

import admin_bot_functions.deeplinking.storage.Deeplink
import helpers.ToMarkdownSupported

class DeeplinkToPageItem(
    private val mOrder: Int
) : Deeplink.Mapper<String> {

    override fun map(id: Int, name: String, code: String, link: String, countUsers: Int) = buildString {
        appendLine("*$mOrder\\.* *${ToMarkdownSupported.Base(name).convertedString()}*")
        appendLine("*Ссылка\\:* ${ToMarkdownSupported.Base(link).convertedString()}")
        appendLine("Количество пользователей\\:")
        append("*$countUsers*")
    }
}
package admin_bot_functions.deeplinking

import admin_bot_functions.deeplinking.handling.DeeplinkToPageItem
import admin_bot_functions.deeplinking.storage.Deeplink

interface DeeplinkPage {

    fun pageMessage(
        deeplink: List<Deeplink>,
        page: Int,
        pageTotal: Int
    ): String

    class Base : DeeplinkPage {

        override fun pageMessage(deeplink: List<Deeplink>, page: Int, pageTotal: Int) = buildString {
            appendLine("*Ссылки с параметрами*")
            if (deeplink.isNotEmpty()) {
                appendLine("Страница $page из $pageTotal")
                appendLine()
                for (i in deeplink.indices) {
                    appendLine(
                        deeplink[i].map(
                            DeeplinkToPageItem(
                                if (page == 1) {
                                    i + 1
                                } else {
                                    i + page * 5
                                }
                            )
                        )
                    )
                }
            } else {
                appendLine()
                appendLine(
                    "Здесь пока пусто\\. Нажмите на кнопку 'Создать ссылку' чтобы создать новую ссылку\\. " +
                            "После создания она отобразиться здесь"
                )
            }
        }
    }
}
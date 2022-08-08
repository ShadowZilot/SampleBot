package admin_bot_functions.deeplinking

import admin_bot_functions.deeplinking.storage.DeeplinkStorage
import executables.EditTextMessage
import executables.Executable
import keyboard_markup.InlineButton
import keyboard_markup.InlineKeyboardMarkup

interface DeeplinkPageMessage {

    fun page(pageNumber: Int) : Executable

    class Base(
        private val mKey: String,
        private val mDeeplink : DeeplinkStorage
    ) : DeeplinkPageMessage {
        private val mPageGenerator = DeeplinkPage.Base()

        override fun page(pageNumber: Int) = EditTextMessage(
            mKey,
            mPageGenerator.pageMessage(
                mDeeplink.deeplinkGrade(pageNumber),
                pageNumber,
                mDeeplink.deeplinkPageCount()
            ),
            mMarkup = InlineKeyboardMarkup(
                listOf(
                    if (mDeeplink.deeplinkPageCount() != 0) {
                        listOf(
                            InlineButton(
                                "⬅️",
                                mCallbackData = "previousDeeplinkPage"
                            ),
                            InlineButton(
                                "$pageNumber/${mDeeplink.deeplinkPageCount()}",
                                mCallbackData = "deeplinkPageCount"
                            ),
                            InlineButton(
                                "➡️",
                                mCallbackData = "nextDeeplinkPage"
                            )
                        )
                    } else {
                        emptyList()
                    },
                    listOf(
                        InlineButton(
                            "Создать ссылку",
                            mCallbackData = "createDeeplink"
                        )
                    ),
                    listOf(
                        InlineButton(
                            "Вернуться",
                            mCallbackData = "backToAdminPanel"
                        )
                    )
                )
            )
        )
    }
}
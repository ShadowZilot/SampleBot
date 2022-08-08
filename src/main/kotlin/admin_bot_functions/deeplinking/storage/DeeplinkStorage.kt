package admin_bot_functions.deeplinking.storage

import admin_bot_functions.deeplinking.handling.GeneratingDeeplinkCode
import admin_bot_functions.deeplinking.handling.PlusUserToDeeplink
import admin_bot_functions.deeplinking.handling.SameLinkCode
import kotlin.math.ceil

interface DeeplinkStorage {

    fun deeplinkPageCount(): Int

    fun deeplinkGrade(order: Int): List<Deeplink>

    fun createNewDeeplink(name: String)

    fun plusUserToLink(code: String)

    class Base(
        private val mStore: DeeplinkFileHandling,
        private val mBotName: String
    ) : DeeplinkStorage {
        private val mLinks = mStore.load()

        override fun deeplinkPageCount() = ceil(mLinks.size % 5.0).toInt()

        override fun deeplinkGrade(order: Int): List<Deeplink> {
            return if (mLinks.isNotEmpty()) {
                mLinks.reversed().subList(
                    order * 5 - 5,
                    if (order * 5 - 1 > mLinks.size) {
                        mLinks.size - 1
                    } else {
                        order * 5 - 1
                    }
                )
            } else {
                emptyList()
            }
        }

        override fun createNewDeeplink(name: String) {
            val codeGenerator = GeneratingDeeplinkCode.Validating(
                mLinks,
                GeneratingDeeplinkCode.Base()
            )
            val generatedCode = codeGenerator.generateCode(name)
            mLinks.add(
                Deeplink(
                    name,
                    generatedCode,
                    "https://t.me/$mBotName?start=$generatedCode",
                    0
                )
            )
            mStore.cache(mLinks)
        }

        override fun plusUserToLink(code: String) {
            val link = mLinks.find {
                it.map(SameLinkCode(code))
            } ?: throw NotFoundDeeplink(code)
            val index = mLinks.indexOf(link)
            mLinks.removeAt(index)
            mLinks.add(
                index, link.map(
                    PlusUserToDeeplink()
                )
            )
        }
    }
}
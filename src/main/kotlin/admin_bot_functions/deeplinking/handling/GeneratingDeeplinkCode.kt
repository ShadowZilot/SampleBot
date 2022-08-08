package admin_bot_functions.deeplinking.handling

import admin_bot_functions.deeplinking.storage.Deeplink
import kotlin.random.Random

private val mSymbols = listOf(
    "a", "b", "c", "d", "e",
    "f", "g", "h", "i", "j",
    "k", "l", "m", "n", "o",
    "p", "q", "r", "s", "t",
    "u", "v", "w", "x", "y",
    "z", "0", "1", "2", "3",
    "4", "5", "6", "7", "8",
    "9"
)

interface GeneratingDeeplinkCode {

    fun generateCode(name: String): String

    class Base: GeneratingDeeplinkCode {

        override fun generateCode(name: String): String {
            val builder = StringBuilder()
            val random = Random(System.currentTimeMillis())
            for (i in 0..5) {
                builder.append(
                    mSymbols.random(random)
                )
            }
            return builder.toString()
        }
    }

    class Validating(
        private val mLinks: List<Deeplink>,
        private val mGenerator: GeneratingDeeplinkCode
    ) : GeneratingDeeplinkCode {

        override fun generateCode(name: String): String {
            var generatedCode = mGenerator.generateCode(name)
            while (mLinks.find {
                    it.map(SameLinkCode(generatedCode))
                } != null) {
                generatedCode = mGenerator.generateCode(name)
            }
            return generatedCode
        }
    }
}
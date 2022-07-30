import chain.*
import core.Bot
import core.BotChains
import handlers.CommandEvent
import handlers.OnCallbackGotten
import handlers.OnTextGotten

class BotChains(
    private val mKey: String
) : BotChains {

    override fun chains() = listOf(
        StartGreeting(
            mKey,
            CommandEvent("/start")
        )
    )
}
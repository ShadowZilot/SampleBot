import chain.StartGreeting
import core.Bot
import core.BotChains
import handlers.CommandEvent

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
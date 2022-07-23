package chain

import core.Updating
import executables.Executable
import executables.SendMessage
import handlers.BotRecognizerEvent
import handlers.UnhandledEvent

class StartGreeting(
    private val mKey: String,
    private val mEvent: BotRecognizerEvent
) : Chain {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return try {
            updating.map(mEvent)
            listOf(
                SendMessage(
                    "Привет\\! Я тестовый бот\\.",
                    mKey
                )
            )
        } catch (e: UnhandledEvent) {
            emptyList()
        }
    }
}
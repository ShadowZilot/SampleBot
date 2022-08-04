package chain

import core.Updating
import executables.Executable
import executables.SendMessage
import handlers.CommandEvent

class StartGreeting : Chain(CommandEvent("/start")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            SendMessage(
                mKey,
                "Привет\\!"
            )
        )
    }
}


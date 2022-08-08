package admin_bot_functions.deeplinking.chain

import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten

class DeeplinkPageButton : Chain(
    OnCallbackGotten("deeplinkPageCount")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return listOf(
            AnswerToCallback(
                mKey,
                "Текущая старница"
            )
        )
    }
}
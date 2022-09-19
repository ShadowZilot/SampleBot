package chain

import Storages
import core.Updating
import executables.Executable
import handlers.BotRecognizerEvent
import handlers.UnhandledEvent
import stConfig

abstract class Chain(
    private val mEvent: BotRecognizerEvent
) {
    protected val mKey = stConfig.configValueString("botKey")
    protected val mStates = Storages.stStateStorage

    open fun checkEvent(updating: Updating) : Boolean {
        return try {
            updating.map(mEvent)
            true
        } catch (e: UnhandledEvent) {
            false
        }
    }
    abstract suspend fun executableChain(updating: Updating): List<Executable>
}
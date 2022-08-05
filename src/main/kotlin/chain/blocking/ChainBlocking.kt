package chain.blocking

import chain.Chain
import core.Updating
import executables.Executable
import handlers.BotRecognizerEvent

class ChainBlocking(
    private val mChain: Chain,
    private val mResolver: Updating.Mapper<Boolean>
) : Chain(BotRecognizerEvent.Dummy) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return if (updating.map(mResolver)) {
            mChain.executableChain(updating)
        } else {
            emptyList()
        }
    }
}
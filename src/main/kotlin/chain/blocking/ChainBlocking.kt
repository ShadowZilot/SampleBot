package chain.blocking

import chain.Chain
import core.Updating
import handlers.BotRecognizerEvent

class ChainBlocking(
    private val mChain: Chain,
    private val mResolver: Updating.Mapper<Boolean>
) : Chain(BotRecognizerEvent.Dummy) {

    override fun checkEvent(updating: Updating) = updating.map(mResolver) && mChain.checkEvent(updating)

    override suspend fun executableChain(updating: Updating) = mChain.executableChain(updating)
}
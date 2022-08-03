package statistic

import chain.Chain
import core.Updating
import executables.Executable
import handlers.CommandEvent

class StartViewingStatisticChain : Chain(CommandEvent("/statistic")) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        return emptyList()
    }
}
package chain

import core.Updating
import executables.Executable

interface Chain {

    suspend fun executableChain(updating: Updating): List<Executable>
}
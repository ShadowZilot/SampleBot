package core

import chain.Chain

interface EventRecognizing : UpdatesGotten {

    class Base(
        private val mBot: Bot,
        private val mChains: List<Chain>
    ) : EventRecognizing {
        private var mMaxId = 1L

        override suspend fun fetchUpdates(updates: List<Updating>, id: Long) {
            if (id > mMaxId) {
                updates.forEach { updating ->
                    mChains.forEach { chain ->
                        if (chain.checkEvent(updating)) {
                            val executables = chain.executableChain(updating)
                            for (i in executables.indices) {
                                try {
                                    mBot.implementRequest(
                                        updating.map(executables[i]),
                                        executables[i]
                                    )
                                } catch (e: IllegalArgumentException) {
                                    continue
                                }
                            }
                            if (executables.isNotEmpty()) {
                                return
                            }
                        }
                    }
                }
                mMaxId = id
            }
        }
    }
}

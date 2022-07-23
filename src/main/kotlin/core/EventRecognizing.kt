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
                updates.forEach {
                    mChains.forEach { chain ->
                        val executables = chain.executableChain(it)
                        for (i in executables.indices) {
                            try {
                                mBot.implementRequest(it.map(executables[i]), executables[i])
                            } catch (e: IllegalArgumentException) {
                                continue
                            }
                        }
                        if (executables.isNotEmpty()) {
                            return
                        }
                    }
                }
                mMaxId = id
            }
        }
    }
}

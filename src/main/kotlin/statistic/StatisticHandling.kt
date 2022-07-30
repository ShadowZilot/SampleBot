package statistic

import helpers.storage.StorageHandling

interface StatisticHandling {

    fun writeStatistic(
        userId: Long,
        chatId: Long,
        type: StatisticType,
        parameters: List<Pair<String, Any>>
    )

    class Base(
        private val mStore: StorageHandling<StatisticItem>
    ) : StatisticHandling {
        private val mStates = mStore.load()

        override fun writeStatistic(
            userId: Long,
            chatId: Long,
            type: StatisticType,
            parameters: List<Pair<String, Any>>
        ) {
            mStates.add(
                StatisticItem(
                    userId,
                    chatId,
                    type.typeName(),
                    parameters,
                    System.currentTimeMillis()
                )
            )
            mStore.cache(mStates)
        }
    }
}
package admin_bot_functions.statistic.storage

import core.Updating
import helpers.storage.StorageHandling
import admin_bot_functions.statistic.form_stat.IsStatItemFit
import updating.UpdatingChatId
import updating.UserIdUpdating

interface StatisticHandling {

    fun statSliceByDate(statType: StatisticType, dateRange: LongRange): List<StatisticItem>

    fun writeStatistic(
        userId: Long,
        chatId: Long,
        type: StatisticType,
        parameters: List<Pair<String, Any>> = emptyList()
    )

    fun writeStatistic(
        updating: Updating,
        type: StatisticType,
        parameters: List<Pair<String, Any>> = emptyList()
    )

    class Base(
        private val mStore: StorageHandling<StatisticItem>
    ) : StatisticHandling {
        private val mStates = mStore.load()

        override fun statSliceByDate(statType: StatisticType, dateRange: LongRange): List<StatisticItem> {
            val finder = IsStatItemFit(
                dateRange,
                statType
            )
            return mutableListOf<StatisticItem>().apply {
                for (i in mStates.indices) {
                    if (mStates[i].map(finder)) {
                        add(mStates[i])
                    }
                }
            }
        }

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

        override fun writeStatistic(updating: Updating,
                                    type: StatisticType,
                                    parameters: List<Pair<String, Any>>) {
            writeStatistic(
                updating.map(UserIdUpdating()),
                updating.map(UpdatingChatId()).second,
                type,
                parameters
            )
        }
    }
}
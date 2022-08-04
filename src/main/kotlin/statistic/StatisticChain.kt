package statistic

import Storages
import core.BotChains

class StatisticChain(
    private val mStatistics: StatisticHandling
) : BotChains {

    override fun chains() = listOf(
        StartViewingStatisticChain(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        ActiveUsersChain(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        BackToStatViewing(),
        NewComingUsersStat(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        ActionsStat(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics)
    )
}
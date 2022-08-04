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
        ActiveUsersChain(),
        BackToStatViewing(),
        NewComingUsersStat(),
        ActionsStat(),
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics)
    )
}
package statistic

import core.BotChains

class StatisticChain(
    private val mStatistics: StatisticHandling
) : BotChains {

    override fun chains() = listOf(
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics),
        StartViewingStatisticChain(),
        ActiveUsersChain(),
        BackToStatViewing(),
        NewComingUsersStat(),
        ActionsStat()
    )
}
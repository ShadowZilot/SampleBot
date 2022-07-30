package statistic

import core.BotChains
import handlers.CommandEvent

class StatisticChain(
    private val mStatistics: StatisticHandling
) : BotChains {

    override fun chains() = listOf(
        NewComingChain(
            CommandEvent("/start"),
            mStatistics
        ),
        CommonActionChain(mStatistics)
    )
}
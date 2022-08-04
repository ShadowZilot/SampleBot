package statistic.chains

import Storages
import core.BotChains
import statistic.storage.StatisticHandling
import statistic.period_time.StatisticsTimePeriod

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
        PointStartDateChain(),
        PointStartDateChainFinal(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        CancelEnteringDateChain(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            )
        ),
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics)
    )
}
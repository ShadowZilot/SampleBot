package statistic.chains

import Storages
import core.BotChains
import statistic.StatisticMessage
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
        GoToNextStatPeriod(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            ),
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        GoToPreviousStatPeriod(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            ),
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        PointStartDateChain(),
        PointStartDateChainFinal(
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        PointEndDateChain(),
        PointEndDateChainFinal(
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        CancelEnteringDateChain(
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics)
    )
}
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
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        BackToStatViewing(),
        NewComingUsersStat(
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        ActionsStat(
            StatisticMessage.Base(
                Storages.stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
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
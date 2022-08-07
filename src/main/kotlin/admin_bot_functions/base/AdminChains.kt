package admin_bot_functions.base

import Storages
import admin_bot_functions.base.chains.BackToAdminPanelChain
import admin_bot_functions.base.chains.ViewAdminPanelChain
import admin_bot_functions.statistic.StatisticMessage
import admin_bot_functions.statistic.chains.*
import admin_bot_functions.statistic.period_time.StatisticsTimePeriod
import chain.blocking.ChainBlocking
import core.BotChains

class AdminChains : BotChains {
    override fun chains() = listOf(
        ViewAdminPanelChain(),
        BackToAdminPanelChain(),
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
        AllTimeUsersChain(
            Storages.stStatistics
        ),
        RealTimeUsersChain(
            Storages.stStatistics
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
    ).map {
        ChainBlocking(it, IsUserAdmin(Storages.stAdmins))
    }
}
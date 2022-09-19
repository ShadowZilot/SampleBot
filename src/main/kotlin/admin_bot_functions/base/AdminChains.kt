package admin_bot_functions.base

import Storages
import admin_bot_functions.base.chains.BackToAdminPanelChain
import admin_bot_functions.base.chains.ViewAdminPanelChain
import admin_bot_functions.deeplinking.DeeplinkPageMessage
import admin_bot_functions.deeplinking.chain.*
import admin_bot_functions.statistic.StatisticMessage
import admin_bot_functions.statistic.chains.*
import admin_bot_functions.statistic.period_time.StatisticsTimePeriod
import chain.blocking.ChainBlocking
import core.BotChains
import stConfig

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
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        BackToStatViewing(),
        NewComingUsersStat(
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        ActionsStat(
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        GoToNextStatPeriod(
            StatisticsTimePeriod.Base(
                Storages.stStateStorage
            ),
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
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
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        PointStartDateChain(),
        PointStartDateChainFinal(
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        PointEndDateChain(),
        PointEndDateChainFinal(
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        CancelEnteringDateChain(
            StatisticMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stStateStorage,
                Storages.stStatistics
            )
        ),
        GoToDeeplinkPagesChain(
            DeeplinkPageMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stDeeplink
            )
        ),
        GoToNextDeeplinkPage(
            Storages.stDeeplink,
            DeeplinkPageMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stDeeplink
            )
        ),
        GoToPreviousDeeplinkPage(
            DeeplinkPageMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stDeeplink
            )
        ),
        BeginCreateDeeplinkChain(),
        CreateDeeplinkFinalChain(
            Storages.stDeeplink
        ),
        CancelCreatingDeeplink(
            DeeplinkPageMessage.Base(
                stConfig.configValueString("botKey"),
                Storages.stDeeplink
            )
        ),
        DeeplinkPageButton(),
    ).map {
        ChainBlocking(it, IsUserAdmin(Storages.stAdmins))
    }
}
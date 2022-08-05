package admin_bot_functions.statistic.chains

import Storages
import admin_bot_functions.add_new_admins.AddNewAdminChain
import admin_bot_functions.add_new_admins.AddNewAdminChainFinal
import core.BotChains
import admin_bot_functions.statistic.StatisticMessage
import admin_bot_functions.statistic.storage.StatisticHandling
import admin_bot_functions.statistic.period_time.StatisticsTimePeriod

class StatisticChain(
    private val mStatistics: StatisticHandling
) : BotChains {

    override fun chains() = listOf(
        AddNewAdminChain(),
        AddNewAdminChainFinal(
            Storages.stAdmins
        ),
        NewComingChain(mStatistics),
        CommonActionChain(mStatistics)
    )
}
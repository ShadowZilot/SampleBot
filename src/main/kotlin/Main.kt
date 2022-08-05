import admin_bot_functions.base.AdminChains
import core.BaseBot
import core.EventRecognizing
import core.PollingHandling
import core.interceptor.UpdatesInterceptor
import okhttp3.OkHttpClient
import admin_bot_functions.statistic.chains.StatisticChain

val mClient = OkHttpClient.Builder()
    .build()

var sBasePath = ""

fun main(args: Array<String>) {
    sBasePath = try {
        args[0]
    } catch (e: ArrayIndexOutOfBoundsException) {
        ""
    }
    val bot = BaseBot(
        mClient,
        UpdatesInterceptor.Dummy
    )
    val botPolling = PollingHandling(
        bot,
        Storages.stConfig.configValueString("botKey")
    )
    val events = EventRecognizing.Base(
        bot,
        BotChains(
            Storages.stConfig.configValueString("botKey")
        ).chains()
    )
    val adminHandlers = EventRecognizing.Base(
        bot,
        AdminChains().chains()
    )
    val statisticHandlers = EventRecognizing.Base(
        bot,
        StatisticChain(
            Storages.stStatistics
        ).chains()
    )
    bot.addListener(events)
    bot.addListener(statisticHandlers)
    bot.addListener(adminHandlers)
    botPolling.start()
}
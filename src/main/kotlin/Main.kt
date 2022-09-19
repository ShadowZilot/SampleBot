import configuration.Config
import helpers.storage.jdbc_wrapping.DatabaseHelper
import okhttp3.OkHttpClient
import java.io.File

val mClient = OkHttpClient.Builder()
    .build()
val stConfig = Config.Base(File("config.json"))

var sBasePath = ""

fun main(args: Array<String>) {
    sBasePath = try {
        args[0]
    } catch (e: ArrayIndexOutOfBoundsException) {
        ""
    }
//    val bot = BaseBot(
//        mClient,
//        UpdatesInterceptor.Dummy
//    )
//    val botPolling = PollingHandling(
//        bot,
//        stConfig.configValueString("botKey")
//    )
//    val events = EventRecognizing.Base(
//        bot,
//        BotChains(
//            stConfig.configValueString("botKey")
//        ).chains()
//    )
//    val adminHandlers = EventRecognizing.Base(
//        bot,
//        AdminChains().chains()
//    )
//    val statisticHandlers = EventRecognizing.Base(
//        bot,
//        StatisticChain(
//            Storages.stStatistics
//        ).chains()
//    )
//    bot.addListener(events)
//    bot.addListener(statisticHandlers)
//    bot.addListener(adminHandlers)
//    botPolling.start()
    val db = DatabaseHelper.Base.Instance.provideInstance()
    db.executeQueryWithoutResult(
        "INSERT test (name, email) VALUES(\"Egor\", \"e.ponomaryow2017@yandex\")"
    )
}
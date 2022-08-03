import configuration.Config
import helpers.storage.JsonFile
import helpers.storage.StatesFileHelping
import helpers.storage.UserFileHelping
import staging.StateHandling
import statistic.StatisticFileHandling
import statistic.StatisticHandling
import statistic.StatisticItemToJson
import users.AllUsersStorage
import users.UserToJson
import java.io.File

object Storages {
    val stConfig = Config.Base(File("${sBasePath}config.json"))
    val stStateStorage = StateHandling.Base(
        StatesFileHelping(
            JsonFile.Base(File("${sBasePath}states.json"))
        )
    )
    val stUsersStorage = AllUsersStorage.Base(
        UserFileHelping(
            JsonFile.Base(File("${sBasePath}allUsers.json")),
            UserToJson()
        )
    )
    val stStatistics = StatisticHandling.Base(
        StatisticFileHandling(
            JsonFile.Base(File("${sBasePath}statistic.json")),
            StatisticItemToJson()
        )
    )
}
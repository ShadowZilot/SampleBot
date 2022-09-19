import admin_bot_functions.admins_storage.AdminToJson
import admin_bot_functions.admins_storage.AdminsFileHandling
import admin_bot_functions.admins_storage.AdminsHandling
import admin_bot_functions.deeplinking.handling.DeeplinkToJson
import admin_bot_functions.deeplinking.storage.DeeplinkFileHandling
import admin_bot_functions.deeplinking.storage.DeeplinkStorage
import admin_bot_functions.statistic.storage.StatisticFileHandling
import admin_bot_functions.statistic.storage.StatisticHandling
import helpers.storage.StatesFileHelping
import helpers.storage.UserFileHelping
import helpers.storage.edb_commons.EDBConnection
import helpers.storage.jdbc_wrapping.DatabaseHelper
import staging.StateHandling
import users.AllUsersStorage
import users.UserToJson

object Storages {
    private val mDatabase = DatabaseHelper.Base.Instance.provideInstance()

    val stAdmins = AdminsHandling.Base(
        AdminsFileHandling(
            EDBConnection.Base(
                "admins.edb"
            ), AdminToJson()
        )
    )
    val stStateStorage = StateHandling.Base(
        StatesFileHelping(
            EDBConnection.Base("states.edb")
        )
    )
    val stUsersStorage = AllUsersStorage.Base(
        "users",
        mDatabase
    ).apply {
        mDatabase.executeQuery(this.tableSchema())
    }
    val stStatistics = StatisticHandling.Base(
        StatisticFileHandling(
            EDBConnection.Base("statistic.edb")
        )
    )

    val stDeeplink = DeeplinkStorage.Base(
        DeeplinkFileHandling(
            EDBConnection.Base("deeplink.edb"),
            DeeplinkToJson()
        ),
        stConfig.configValueString("botName")
    )
}
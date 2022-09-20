import admin_bot_functions.admins_storage.AdminsHandling
import admin_bot_functions.deeplinking.storage.DeeplinkStorage
import admin_bot_functions.statistic.storage.StatisticHandling
import helpers.storage.jdbc_wrapping.DatabaseHelper
import staging.StateHandling
import users.AllUsersStorage

object Storages {
    private val mDatabase = DatabaseHelper.Base.Instance.provideInstance()

    val stAdmins = AdminsHandling.Base(
        "admins",
        mDatabase
    )
    val stStateStorage = StateHandling.Base(
        "states",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }
    val stUsersStorage = AllUsersStorage.Base(
        "users",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }
    val stStatistics = StatisticHandling.Base(
        "statistics",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }
    val stDeeplink = DeeplinkStorage.Base(
        "deeplink",
        stConfig.configValueString("botName"),
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }
}
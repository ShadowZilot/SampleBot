package admin_bot_functions.deeplinking.storage

import admin_bot_functions.deeplinking.handling.GeneratingDeeplinkCode
import admin_bot_functions.deeplinking.handling.PlusUserToDeeplink
import helpers.storage.StorageShell
import helpers.storage.jdbc_wrapping.DatabaseHelper
import kotlin.math.ceil

interface DeeplinkStorage : StorageShell {

    fun deeplinkPageCount(): Int

    fun deeplinkGrade(order: Int): List<Deeplink>

    fun createNewDeeplink(name: String)

    fun plusUserToLink(code: String)

    fun isCodeExist(code: String): Boolean

    class Base(
        private val mTableName: String,
        private val mBotName: String,
        private val mConnector: DatabaseHelper
    ) : DeeplinkStorage {

        override fun deeplinkPageCount() : Int {
            var linksSize = 0
            mConnector.executeQuery(
                "select count(*) as links_size from `$mTableName`"
            ) { result, _ ->
                linksSize = result.getInt("links_size")
            }
            return ceil(linksSize / 5.0).toInt()
        }

        override fun deeplinkGrade(order: Int): List<Deeplink> {
            val links = mutableListOf<Deeplink>().apply {
                mConnector.executeQuery(
                    "select * from `$mTableName`"
                ) { result, next ->
                    var isNext = next
                    while (isNext) {
                        add(Deeplink(result))
                        isNext = result.next()
                    }
                }
            }
            return if (links.isNotEmpty()) {
                links.reversed().subList(
                    order * 5 - 5,
                    if (order * 5 > links.size) {
                        links.size
                    } else {
                        order * 5
                    }
                )
            } else {
                emptyList()
            }
        }

        override fun createNewDeeplink(name: String) {
            val codeGenerator = GeneratingDeeplinkCode.Validating(
                this,
                GeneratingDeeplinkCode.Base()
            )
            val generatedCode = codeGenerator.generateCode(name)
            mConnector.createTable(
                Deeplink(
                    0,
                    name,
                    generatedCode,
                    "https://t.me/$mBotName?start=$generatedCode",
                    0
                ).insertSQLQuery(mTableName)
            )
        }

        override fun plusUserToLink(code: String) {
            mConnector.executeQuery(
                "select * from `$mTableName` where `code` = '$code'"
            ) { result, _ ->
                mConnector.executeQueryWithoutResult(
                    Deeplink(result).map(
                        PlusUserToDeeplink()
                    ).updateSQLQuery(
                        mTableName
                    )
                )
            }
        }

        override fun isCodeExist(code: String): Boolean {
            var result = false
            mConnector.executeQuery(
                "select `code` from `$mTableName` where `code` = '$code'"
            ) { _, empty ->
                result = empty
            }
            return result
        }

        override fun tableSchema() = "create table `$mTableName` (" +
                "id int primary key auto_increment, " +
                "name text, " +
                "code text, " +
                "link text, " +
                "count_users int" +
                ");"

        override fun tableName() = mTableName
    }
}
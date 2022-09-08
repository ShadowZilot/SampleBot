package admin_bot_functions.statistic.storage

import helpers.storage.EDBConnection
import helpers.storage.StorageHandling
import org.json.JSONObject

class StatisticFileHandling(
    file: EDBConnection
    ) : StorageHandling<StatisticItem>(file) {

    override fun insert(data: StatisticItem) {

    }

    override fun update(data: StatisticItem) {

    }

    override fun delete(data: StatisticItem) {

    }

    override fun read(): StatisticItem {
        return StatisticItem(JSONObject())
    }

    override fun readNext(): StatisticItem {
        return StatisticItem(JSONObject())
    }

    override fun hasNext(): Boolean {
        return true
    }

    override fun reset() {

    }
}
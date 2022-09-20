package staging

import helpers.storage.Record
import org.json.JSONArray
import org.json.JSONObject
import java.sql.ResultSet

data class State(
    val mUserId: Long,
    private val mValues: List<Pair<String, Any>> = emptyList()
) : Record() {

    constructor(
        otherState: State,
        values: List<Pair<String, Any>>,
        valuesToDelete: List<String> = emptyList()
    ) : this(
        otherState.mUserId,
        mutableListOf<Pair<String, Any>>().apply {
            val summa = otherState.mValues.merge(values)
            for (i in summa.indices) {
                if (!valuesToDelete.contains(summa[i].first)) {
                    this.add(summa[i])
                }
            }
        }
    )

    constructor(oldState: State, newState: State) : this(
        oldState.mUserId,
        oldState.mValues.merge(newState.mValues)
    )

    constructor(resultSet: ResultSet) : this(
        resultSet.getLong("user_id"),
        mutableListOf<Pair<String, Any>>().apply {
            val array = JSONArray(resultSet.getString("codes"))
            for (i in 0 until array.length()) {
                this.add(
                    Pair(
                        array.getJSONObject(i).getString("code"),
                        array.getJSONObject(i).get("data")
                    )
                )
            }
        }
    )

    fun editor(states: StateHandling) = StateEditor.Base(
        states,
        this
    )

    fun string(key: String): String {
        return try {
            mValues.find {
                it.first == key
            }?.second as String
        } catch (e: java.lang.Exception) {
            throw NotFoundStateValue(mUserId, key)
        }
    }

    fun int(key: String): Int {
        return try {
            mValues.find {
                it.first == key
            }?.second as Int
        } catch (e: java.lang.Exception) {
            throw NotFoundStateValue(mUserId, key)
        }
    }

    fun boolean(key: String): Boolean {
        return try {
            mValues.find {
                it.first == key
            }?.second as Boolean
        } catch (e: java.lang.Exception) {
            throw NotFoundStateValue(mUserId, key)
        }
    }

    fun long(key: String): Long {
        return try {
            mValues.find {
                it.first == key
            }?.second as Long
        } catch (e: java.lang.Exception) {
            throw NotFoundStateValue(mUserId, key)
        }
    }

    override fun insertSQLQuery(tableName: String) = "insert into `$tableName`" +
            " (`user_id`, `codes`) " +
            " values($mUserId, '${
                JSONArray().apply {
                    for (i in mValues.indices) {
                        put(JSONObject().apply {
                            put("code", mValues[i].first)
                            put("data", mValues[i].second)
                        })
                    }
                }
            }')"

    override fun updateSQLQuery(tableName: String) = "update `$tableName` set `codes` = '${
        JSONArray().apply {
            for (i in mValues.indices) {
                put(JSONObject().apply {
                    put("code", mValues[i].first)
                    put("data", mValues[i].second)
                })
            }
        }
    }' where `user_id` = $mUserId"

    override fun deleteSQLQuery(tableName: String) = "delete * from `$tableName` where `user_id` = $mUserId"
}

fun State.safetyString(key: String): String = try {
    string(key)
} catch (e: NotFoundStateValue) {
    ""
}

fun State.safetyBoolean(key: String): Boolean = try {
    boolean(key)
} catch (e: NotFoundStateValue) {
    false
}

fun List<Pair<String, Any>>.merge(newList: List<Pair<String, Any>>): List<Pair<String, Any>> {
    return mutableListOf<Pair<String, Any>>().apply {
        addAll(newList)
        for (i in this@merge.indices) {
            if (this.find {
                    it.first == this@merge[i].first
                } == null) {
                add(this@merge[i])
            }
        }
    }
}
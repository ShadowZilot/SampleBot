package staging

import org.json.JSONArray
import org.json.JSONObject

data class State(
    val mUserId: Long,
    private val mValues: List<Pair<String, Any>> = emptyList()
) {

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

    constructor(item: JSONObject) : this(
        item.getLong("user_id"),
        mutableListOf<Pair<String, Any>>().apply {
            val array = item.getJSONArray("codes")
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

    fun toJson() = JSONObject().apply {
        put("user_id", mUserId)
        put("codes", JSONArray().apply {
            mValues.forEach {
                this.put(JSONObject().apply {
                    put("code", it.first)
                    put("data", it.second)
                })
            }
        })
    }

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
}

fun State.safetyString(key: String): String = try {
    string(key)
} catch (e: NotFoundStateValue) {
    ""
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
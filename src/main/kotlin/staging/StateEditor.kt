package staging

interface StateEditor {

    fun putString(key: String, data: String)

    fun putInt(key: String, data: Int)

    fun putBoolean(key: String, data: Boolean)

    fun putLong(key: String, data: Long)

    fun deleteValue(key: String)

    fun commit(): State

    class Base(
        private val mStates: StateHandling,
        private val mOldState: State
    ) : StateEditor {
        private val mNewValues = mutableListOf<Pair<String, Any>>()
        private val mDeleteValues = mutableListOf<String>()

        override fun putString(key: String, data: String) {
            if (mNewValues.containsKey(key)) {
                mNewValues.remove(mNewValues.find {
                    it.first == key
                })
            }
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putInt(key: String, data: Int) {
            if (mNewValues.containsKey(key)) {
                mNewValues.remove(mNewValues.find {
                    it.first == key
                })
            }
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putBoolean(key: String, data: Boolean) {
            if (mNewValues.containsKey(key)) {
                mNewValues.remove(mNewValues.find {
                    it.first == key
                })
            }
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putLong(key: String, data: Long) {
            if (mNewValues.containsKey(key)) {
                mNewValues.remove(mNewValues.find {
                    it.first == key
                })
            }
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun deleteValue(key: String) {
            mDeleteValues.add(key)
        }

        override fun commit(): State {
            val newState = State(
                mOldState,
                mNewValues,
                mDeleteValues
            )
            mStates.updateState(newState)
            return newState
        }
    }
}

fun List<Pair<String, Any>>.containsKey(key: String): Boolean {
    for (i in indices) {
        if (this[i].first == key) {
            return true
        }
    }
    return false
}
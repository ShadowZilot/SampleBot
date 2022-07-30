package staging

interface StateEditor {

    fun putString(key: String, data: String)

    fun putInt(key: String, data: Int)

    fun putBoolean(key: String, data: Boolean)

    fun putLong(key: String, data: Long)

    fun deleteValue(key: String)

    fun commit() : State

    class Base(
        private val mStates: StateHandling,
        private val mOldState: State
    ) : StateEditor {
        private val mNewValues = mutableListOf<Pair<String, Any>>()
        private val mDeleteValues = mutableListOf<String>()

        override fun putString(key: String, data: String) {
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putInt(key: String, data: Int) {
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putBoolean(key: String, data: Boolean) {
            mNewValues.add(
                Pair(key, data)
            )
        }

        override fun putLong(key: String, data: Long) {
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
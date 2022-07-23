package stating

import core.Updating
import helpers.storage.StorageHandling
import updating.UserIdUpdating

interface StateHandling {

    fun writeState(state: State, index: Int = 0)

    fun updateState(state: State)

    fun state(id: Long): State

    fun state(updating: Updating): State

    fun deleteState(id: Long)

    class Base(
        private val mStore: StorageHandling<State>
    ) : StateHandling {
        private val mStates = mStore.load()

        override fun writeState(state: State, index: Int) {
            mStates.add(index, state)
            mStore.cache(mStates)
        }

        override fun updateState(state: State) {
            val mapper = SameState(state)
            val oldState = mStates.find {
                it.map(mapper)
            }
            if (oldState != null) {
                val index = mStates.indexOf(
                    oldState
                )
                mStates.removeAt(index)
                writeState(state, index)
            } else {
                writeState(state)
            }
        }

        override fun state(id: Long): State {
            val finder = SameStateID(id)
            return mStates.find {
                it.map(finder)
            } ?: return State(id, listOf())
        }

        override fun state(updating: Updating) : State {
            return state(updating.map(UserIdUpdating()))
        }

        override fun deleteState(id: Long) {
            val mapper = SameStateID(id)
            mStates.remove(
                mStates.find {
                    it.map(mapper)
                }
            )
            mStore.cache(mStates)
        }
    }
}
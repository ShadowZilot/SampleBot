package staging

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

        override fun writeState(state: State, index: Int) {
//            mStates.add(index, state)
//            mStore.cache(mStates)
        }

        override fun updateState(state: State) {
//            val oldState = mStates.find {
//                it.mUserId == state.mUserId
//            }
//            if (oldState != null) {
//                val index = mStates.indexOf(
//                    oldState
//                )
//                mStates.removeAt(index)
//                writeState(state, index)
//            } else {
//                writeState(state)
//            }
        }

        override fun state(id: Long): State {
//            return mStates.find {
//                it.mUserId == id
//            } ?: return State(id, listOf())
            throw NotFoundState()
        }

        override fun state(updating: Updating) : State {
            return state(updating.map(UserIdUpdating()))
        }

        override fun deleteState(id: Long) {
//            mStates.remove(
//                mStates.find {
//                    it.mUserId == id
//                }
//            )
//            mStore.cache(mStates)
        }
    }
}
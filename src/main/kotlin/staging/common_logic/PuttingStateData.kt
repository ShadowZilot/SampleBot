package stating.common_logic

import stating.State

class PuttingStateData(
    private val mData: Pair<String, String>
) : State.Mapper<State> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): State {
        val sameData = stateCode.find {
            it.first == mData.first
        }
        return if (sameData != null) {
            State(
                userId,
                stateCode.toMutableList().apply {
                    val index = indexOf(sameData)
                    removeAt(index)
                    add(index, Pair(sameData.first, mData.second))
                }
            )
        } else {
            State(
                userId,
                stateCode.toMutableList().apply {
                    add(mData)
                }
            )
        }
    }
}
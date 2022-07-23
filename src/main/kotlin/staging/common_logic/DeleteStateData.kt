package stating.common_logic

import stating.State

class DeleteStateData(
    private val mData: Pair<String, String>
) : State.Mapper<State> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): State {
        val sameData = stateCode.find {
            it.first == mData.first && (mData.second.isEmpty() || it.second == mData.second)
        }
        return if (sameData != null) {
            State(
                userId,
                stateCode.toMutableList().apply {
                    remove(sameData)
                })
        } else {
            State(
                userId,
                stateCode
            )
        }
    }
}
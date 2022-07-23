package stating.common_logic

import stating.State

class IsStateData(
    private val mData: Pair<String, String>,
    private val mIsDeleteData: Boolean = true
) : State.Mapper<Pair<State, Boolean>> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): Pair<State, Boolean> {
        val sameData = stateCode.find {
            it.first == mData.first && (mData.second.isEmpty() || it.second == mData.second)
        }
        return if (sameData != null) {
            val newCodes = if (mIsDeleteData) {
                stateCode.toMutableList().apply {
                    remove(sameData)
                }
            } else {
                stateCode
            }
            Pair(
                State(userId, newCodes),
                true
            )
        } else {
            Pair(
                State(
                    userId,
                    stateCode
                ),
                false
            )
        }
    }
}
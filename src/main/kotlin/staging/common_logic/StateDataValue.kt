package stating.common_logic

import stating.State

class StateDataValue(
    private val mVKey: String
) : State.Mapper<String> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): String {
        val sameData = stateCode.find {
            it.first == mVKey
        }
        return sameData?.second ?: ""
    }
}
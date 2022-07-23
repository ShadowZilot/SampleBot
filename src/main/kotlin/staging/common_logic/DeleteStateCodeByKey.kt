package stating.common_logic

import stating.State

class DeleteStateCodeByKey(
    private val mKey: String
) : State.Mapper<State> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): State {
        val sameData = stateCode.find {
            it.first == mKey
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
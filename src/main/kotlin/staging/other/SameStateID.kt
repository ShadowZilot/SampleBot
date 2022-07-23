package stating

class SameStateID(
    private val mId: Long
) : State.Mapper<Boolean> {
    override fun map(userId: Long, stateCode: List<Pair<String, String>>): Boolean {
        return mId == userId
    }
}
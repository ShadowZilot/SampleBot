package stating

class SameState(
    private val mOther: State
) : State.Mapper<Boolean> {

    override fun map(userId: Long, stateCode: List<Pair<String, String>>): Boolean {
        val outerId = userId
        return mOther.map(object : State.Mapper<Boolean> {
            override fun map(userId: Long, stateCode: List<Pair<String, String>>): Boolean {
                return userId == outerId
            }
        })
    }
}
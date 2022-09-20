package states_storage_test

import helpers.storage.jdbc_wrapping.DatabaseHelper
import staging.State
import staging.StateHandling
import kotlin.test.Test
import kotlin.test.assertEquals

class StateStorageTests {
    private val mDatabase = DatabaseHelper.Base.Instance.provideInstance()
    private val mStates = StateHandling.Base(
        "states",
        mDatabase
    ).apply {
        mDatabase.createTable(tableSchema())
    }

    @Test
    fun baseCreationTest() {
        val actualState = mStates.state(23L)
        assertEquals(
            State(23L, listOf()),
            actualState
        )
    }

    @Test
    fun insertingStateValue() {
        val actualState = mStates.state(44L)
        actualState.editor(mStates).apply {
            putString("name", "Mike")
            putInt("age", 22)
        }.commit()
        assertEquals(
            State(
                44L, listOf(
                    Pair("name", "Mike"),
                    Pair("age", 22)
                )
            ),
            mStates.state(44L)
        )
    }
}
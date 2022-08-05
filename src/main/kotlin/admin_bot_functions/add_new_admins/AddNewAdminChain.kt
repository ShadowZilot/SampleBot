package admin_bot_functions.add_new_admins

import chain.Chain
import core.Updating
import executables.DeleteMessage
import executables.Executable
import executables.SendMessage
import handlers.CommandEvent

class AddNewAdminChain: Chain(
    CommandEvent("/addadmin")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putBoolean("isAwaitForBotPassword", true)
        }
        return listOf(
            DeleteMessage(
                mKey,
                updating
            ),
            SendMessage(
                mKey,
                "Введите пароль"
            ) {
                mStates.state(updating).editor(mStates).apply {
                    putLong("addNewAdminMessage", it.toLong())
                }.commit()
            }
        )
    }
}
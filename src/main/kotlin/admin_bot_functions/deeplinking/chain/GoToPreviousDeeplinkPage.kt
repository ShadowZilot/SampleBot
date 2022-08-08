package admin_bot_functions.deeplinking.chain

import admin_bot_functions.deeplinking.DeeplinkPageMessage
import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten

class GoToPreviousDeeplinkPage(
    private val mMessage: DeeplinkPageMessage
) : Chain(
    OnCallbackGotten("previousDeeplinkPage")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        val currentPage = mStates.state(updating).int("deeplinkPageNumber")
        return if (currentPage == 1) {
            listOf(
                AnswerToCallback(
                    mKey,
                    "Это первая страница!"
                )
            )
        } else {
            mStates.state(updating).editor(mStates).apply {
                putInt("deeplinkPageNumber", currentPage - 1)
            }.commit()
            listOf(
                AnswerToCallback(
                    mKey,
                    "Перехожу на предыдущею страницу"
                ),
                mMessage.page(
                    mStates.state(updating).int("deeplinkPageNumber")
                )
            )
        }
    }
}
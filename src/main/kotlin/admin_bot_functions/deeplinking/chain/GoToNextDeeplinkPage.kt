package admin_bot_functions.deeplinking.chain

import admin_bot_functions.deeplinking.DeeplinkPageMessage
import admin_bot_functions.deeplinking.storage.DeeplinkStorage
import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten

class GoToNextDeeplinkPage(
    private val mDeeplink: DeeplinkStorage,
    private val mMessage: DeeplinkPageMessage
) : Chain(
    OnCallbackGotten("nextDeeplinkPage")
) {
    override suspend fun executableChain(updating: Updating): List<Executable> {
        val currentPage = mStates.state(updating).int("deeplinkPageNumber")
        return if (currentPage == mDeeplink.deeplinkPageCount()) {
            listOf(
                AnswerToCallback(
                    mKey,
                    "Это последняя страница!"
                )
            )
        } else {
            mStates.state(updating).editor(mStates).apply {
                putInt("deeplinkPageNumber", currentPage + 1)
            }.commit()
            listOf(
                AnswerToCallback(
                    mKey,
                    "Перехожу на следующею страницу"
                ),
                mMessage.page(
                    mStates.state(updating).int("deeplinkPageNumber")
                )
            )
        }
    }
}
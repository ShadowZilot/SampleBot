package admin_bot_functions.deeplinking.chain

import admin_bot_functions.deeplinking.DeeplinkPageMessage
import chain.Chain
import core.Updating
import executables.AnswerToCallback
import executables.Executable
import handlers.OnCallbackGotten

class GoToDeeplinkPagesChain(
    private val mDeeplinkPage: DeeplinkPageMessage
) : Chain(
    OnCallbackGotten("goToLinkEditor")
) {

    override suspend fun executableChain(updating: Updating): List<Executable> {
        mStates.state(updating).editor(mStates).apply {
            putInt("deeplinkPageNumber", 1)
        }.commit()
        return listOf(
            AnswerToCallback(
                mKey,
                "Перехожу к редакторы ссылок"
            ),
            mDeeplinkPage.page(
                mStates.state(updating).int("deeplinkPageNumber")
            )
        )
    }
}
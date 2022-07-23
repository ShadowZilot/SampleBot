package core

import chain.Chain

interface BotChains {

    fun chains(): List<Chain>
}
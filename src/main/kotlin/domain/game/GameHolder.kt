package org.tumba.frodo.domain.game

class GameHolder {

    var game: Game? = null

    companion object {

        val instance by lazy { GameHolder() }
    }
}
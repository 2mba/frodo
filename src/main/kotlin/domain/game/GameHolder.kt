package org.tumba.frodo.domain.game

import io.reactivex.subjects.Subject


class GameHolder {

    var game: Game? = null
    var gameEventSubject: Subject<Game>? = null

    companion object {

        val instance by lazy { GameHolder() }
    }
}
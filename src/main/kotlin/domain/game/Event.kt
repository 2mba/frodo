package domain.game

import org.tumba.frodo.domain.core.Player

interface IGameEventCallback {

    fun onReceivedEvent(event: Event)
}

sealed class Event {

    sealed class GameEvent

    sealed class UserEvent(player: Player)

}


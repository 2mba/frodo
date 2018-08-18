package domain.dto

import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.Player
import org.tumba.frodo.domain.game.CardStore
import org.tumba.frodo.domain.game.Game
import org.tumba.frodo.domain.game.PlayerState

fun Game.toGameStateDto(): GameStateDto {
    return GameStateDto(
        turnNumber = this.turnNumber,
        players = this.playerStates.map { it.first.toPlayerDto() },
        playerStates = this.playerStates.map { it.second.toPlayerStateDto(it.first.number) },
        storeDto = this.cardStore.toStoreDto()
    )
}

fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        this.number,
        this.name
    )
}

fun PlayerState.toPlayerStateDto(playerNumber: Int): PlayerStateDto {
    return PlayerStateDto(
        playerNumber = playerNumber,
        cards = this.city.buildings,
        sightCards = this.city.sightCards,
        coins = coins
    )
}

fun CardStore.toStoreDto(): StoreDto{
    return StoreDto(
        this.cards.mapNotNull { it as? DevelopmentCard? }
    )
}
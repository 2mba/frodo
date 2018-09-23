package domain.dto

import domain.game.GameState
import domain.game.GameState.CardBuying
import domain.game.GameState.DiceThrowing
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.Player
import org.tumba.frodo.domain.game.CardStore
import org.tumba.frodo.domain.game.DiceThrowResult
import org.tumba.frodo.domain.game.Game
import org.tumba.frodo.domain.game.PlayerState

fun Game.toGameStateDto(): GameStateDto {
    return GameStateDto(
        turnNumber = this.turnNumber,
        players = this.playerStates.map { it.key.toPlayerDto() },
        playerStates = this.playerStates.map { it.value.toPlayerStateDto(it.key.id) },
        storeDto = this.cardStore.toStoreDto(),
        diceThrowResult = this.diceThrowResult?.toDiceThrowResultDto(),
        gameState = this.gameState.toGameStateDto(),
        turnOfPlayer = this.turnOfPlayer.id
    )
}

fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        this.id,
        this.name
    )
}

fun PlayerState.toPlayerStateDto(playerNumber: Int): PlayerStateDto {
    return PlayerStateDto(
        playerId = playerNumber,
        cards = this.cards.buildings,
        sightCards = this.cards.sightCards,
        coins = coins
    )
}

fun CardStore.toStoreDto(): StoreDto{
    return StoreDto(
        this.cards.mapNotNull { it as? DevelopmentCard? }
    )
}

fun DiceThrowResult.toDiceThrowResultDto(): DiceThrowResultDto {
    return DiceThrowResultDto(
        first = first,
        second = second
    )
}

fun GameState.toGameStateDto(): GameProcessStateDto {
    return when (this) {
        DiceThrowing -> GameProcessStateDto.DiceThrowing
        CardBuying -> GameProcessStateDto.CardBuying
    }
}
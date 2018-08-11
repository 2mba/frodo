package domain.dto

import org.tumba.frodo.domain.core.Player
import org.tumba.frodo.domain.game.Game

fun Game.toGameStateDto(): GameStateDto {
    return GameStateDto(
        players = this.playerStates.map { it.first.toPlayerDto() },
        //playerStates = this.playerStates.map { it.second. }
    )
}

fun Player.toPlayerDto(): PlayerDto {
    return PlayerDto(
        this.number,
        this.name
    )
}
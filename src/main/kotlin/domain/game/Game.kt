package org.tumba.frodo.domain.game

import org.tumba.frodo.domain.core.ActionType.*
import org.tumba.frodo.domain.core.Player
import java.util.*

class Game(
    players: List<Player>,
    val seed: Long = System.currentTimeMillis(),
    private val gameStateChangeCallback: IGameStateChangeCallback

) {

    var turnNumber = 0
    val playerStates: List<Pair<Player, PlayerState>> = players.map { it to PlayerStateFactory().createInitialState() }
    val cardStore: CardStore = CardStoreFactory().createCardStore()
    val turnOfPlayer: Player = players.first()
    var diceThrowResult: DiceThrowResult = DiceThrowResult.fromOneDiceThrow(0)

    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())

    fun throwDices(option: DiceThrowOption) {
        diceThrowResult = throwDice(option)
        handleDiceThrow(diceThrowResult)

        gameStateChangeCallback.onGameStateChanged(
            GameStateChange.DiceThrowResult,
            GameStateChange.PlayerStates
        )
    }

    private fun throwDice(option: DiceThrowOption): DiceThrowResult {
        return when (option) {
            DiceThrowOption.USE_ONE -> DiceThrowResult.fromOneDiceThrow(dice.play())
            DiceThrowOption.USE_TWO -> DiceThrowResult.fromTwoDiceThrow(dice.play(), dice.play())
        }
    }

    private fun handleDiceThrow(diceThrowResult: DiceThrowResult) {
        playerStates.forEach { player ->
            player.second.city.buildings.filter { it.cost == diceThrowResult.sum }.forEach { building ->
                when (building.actionType) {
                    EarnFromBankInAnyPlayerTurn -> {
                        player.second.coins++
                    }
                    EarnFromBankInMyTurn -> {
                        if (player.first == turnOfPlayer) {
                            player.second.coins++
                        }
                    }
                    EarnFromActivePlayerInTheirTurn -> TODO()
                    EarnFromOtherPlayerInMyTurn -> TODO()
                }
            }
        }
    }
}

interface IGameStateChangeCallback {

    fun onGameStateChanged(vararg change: GameStateChange)

}

enum class GameStateChange {
    PlayerStates,
    Store,
    DiceThrowResult
}

class GameFactory {

    fun create(
        players: List<Player>,
        gameStateChangeCallback: IGameStateChangeCallback,
        seed: Long = System.currentTimeMillis()
    ): Game {
        return Game(players, seed, gameStateChangeCallback)
    }
}

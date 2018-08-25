@file:Suppress("JoinDeclarationAndAssignment")

package org.tumba.frodo.domain.game

import domain.game.GameState
import org.tumba.frodo.domain.core.ActionType.*
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.Player
import java.util.*

@Suppress("MemberVisibilityCanPrivate")
class Game(
    players: List<Player>,
    val seed: Long = System.currentTimeMillis(),
    private val gameStateChangeCallback: IGameStateChangeCallback

) {
    var turnNumber = 0
    val playerStates: Map<Player, PlayerState>
    val cardStore: CardStore
    val turnOfPlayer: Player
    var diceThrowResult: DiceThrowResult? = null
    var gameState: GameState

    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())

    init {
        cardStore = CardStoreFactory().createCardStore()
        turnOfPlayer = players.first()
        playerStates = players.associate { it to PlayerStateFactory().createInitialState() }
        gameState = GameState.DiceThrowing
    }

    fun buyCard(card: DevelopmentCard) {
        requireState(GameState.CardBuying)

        val cardBoughtSuccess = cardStore.buy(card)
        if (!cardBoughtSuccess) {
            throw IllegalArgumentException("Card (${card.cardName}) doesn't exist in store")
        }
        playerStates[turnOfPlayer]?.cards?.add(card)

        gameState = GameState.DiceThrowing
        gameStateChangeCallback.onGameStateChanged(GameStateChange.PlayerStates)
    }

    fun throwDices(option: DiceThrowOption) {
        requireState(GameState.DiceThrowing)

        throwDice(option).also { result ->
            handleDiceThrow(result)
            diceThrowResult = result
        }
        gameState = GameState.CardBuying
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
        playerStates.forEach { player, state ->
            state.cards.buildings
                .filter { it.cost == diceThrowResult.sum }
                .forEach { building ->
                    when (building.actionType) {
                        EarnFromBankInAnyPlayerTurn -> {
                            state.coins++
                        }
                        EarnFromBankInMyTurn -> {
                            if (player == turnOfPlayer) {
                                state.coins++
                            }
                        }
                        EarnFromActivePlayerInTheirTurn -> TODO()
                        EarnFromOtherPlayerInMyTurn -> TODO()
                    }
                }
        }
    }

    private fun requireState(gameState: GameState) {
        if (this.gameState != gameState) {
            throw IllegalStateException("Illegal game state")
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

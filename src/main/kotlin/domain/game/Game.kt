@file:Suppress("JoinDeclarationAndAssignment")

package org.tumba.frodo.domain.game

import domain.game.GameState
import org.tumba.frodo.domain.core.ActionType.*
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.Player
import java.util.*

class Game(
    seed: Long = System.currentTimeMillis(),
    private val players: List<Player>,
    private val gameStateChangeCallback: IGameStateChangeCallback

) {
    val playerStates: Map<Player, PlayerState>
    val cardStore: CardStore
    val currentPlayer: Player
        get() {
            return players.firstOrNull { it.id == currentPlayerId }
                ?: throw IllegalStateException("Illegal player id: $currentPlayerId")
        }
    var diceThrowResult: DiceThrowResult? = null
    var gameState: GameState
    var turnNumber = 0
    var currentPlayerId = players.first().id

    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())

    init {
        cardStore = CardStoreFactory().createCardStore()
        playerStates = players.associate { it to PlayerStateFactory().createInitialState() }
        gameState = GameState.DiceThrowing
    }

    fun buyCard(card: DevelopmentCard) {
        requireState(GameState.CardBuying)

        val cost = cardStore.cards.firstOrNull { it == card }?.cost
            ?: throw IllegalArgumentException("Card (${card.cardName}) doesn't exist in store")
        if (!isEnoughCoins(cost)) {
            throw IllegalArgumentException("Not enough coins")
        }
        if (!cardStore.buy(card)) {
            throw IllegalArgumentException("Can't buy card $card")
        }
        getCurrentPlayerState().apply {
            cards.add(card)
            coins -= cost
        }
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

    fun endTurn() {
        requireState(GameState.CardBuying)

        gameState = GameState.DiceThrowing
        changeCurrentPlayer()
        gameStateChangeCallback.onGameStateChanged(
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
                            if (player == currentPlayer) {
                                state.coins++
                            }
                        }
                        EarnFromActivePlayerInTheirTurn -> TODO()
                        EarnFromOtherPlayerInMyTurn -> TODO()
                    }
                }
        }
    }

    private fun isEnoughCoins(coins: Int): Boolean {
        return getCurrentPlayerState().coins >= coins
    }

    private fun getCurrentPlayerState(): PlayerState {
        return playerStates[currentPlayer] ?: throw IllegalArgumentException("Unknown player: $currentPlayer")
    }

    private fun requireState(gameState: GameState) {
        if (this.gameState != gameState) {
            throw IllegalStateException("Illegal game state. Required $gameState, but current ${this.gameState}")
        }
    }

    private fun changeCurrentPlayer() {
        currentPlayerId = with(players) {
            val idx = indexOfFirst { it.id == currentPlayerId } + 1
            get(idx % size).id
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
        return Game(
            seed = seed,
            players = players,
            gameStateChangeCallback = gameStateChangeCallback
        )
    }
}

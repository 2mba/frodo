package org.tumba.frodo.domain.game

import domain.dto.GameStateDto
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.tumba.frodo.domain.core.ActionType
import org.tumba.frodo.domain.core.ActionType.*
import org.tumba.frodo.domain.core.Player
import java.util.*

class Game(
    players: List<Player>,
    val seed: Long = System.currentTimeMillis()
) {

    var turnNumber = 0
    val gameSubject: BehaviorSubject<Game> = BehaviorSubject.createDefault(this)

    val playerStates: List<Pair<Player, PlayerState>> = players.map { it to PlayerStateFactory().createInitialState() }
    val cardStore: CardStore = CardStoreFactory().createCardStore()
    val turnOfPlayer: Player = players.first()
    var diceThrowResult: DiceThrowResult = DiceThrowResult.fromOneDiceThrow(0)

    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())

    fun throwDices(option: DiceThrowOption) {
        diceThrowResult = throwDice(option)
        handleDiceThrow(diceThrowResult)
        gameSubject.onNext(this)
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

class GameFactory {

    fun create(
        players: List<Player>,
        seed: Long = System.currentTimeMillis()
    ): Game {
        return Game(players, seed)
    }
}

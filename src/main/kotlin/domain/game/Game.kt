package org.tumba.frodo.domain.game

import domain.dto.GameStateDto
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
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
    var firstDiceResult: Int = 0
    var secondDiceResult: Int = 0
    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())

    fun throwDices(option: DiceThrowOption) {
        /*val number = when (option) {
            DiceThrowOption.USE_ONE -> {
                firstDiceResult = dice.play()
            }
            DiceThrowOption.USE_TWO -> dice.play()
        }*/
        turnNumber++
        gameSubject.onNext(this)
    }

    enum class DiceThrowOption {
        USE_ONE,
        USE_TWO
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
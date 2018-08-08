package org.tumba.frodo.game

import org.tumba.frodo.core.Player
import java.util.*

class Game(
    players: List<Player>,
    val seed: Long = System.currentTimeMillis()
){

    val playerStates: List<Pair<Player, PlayerState>> = listOf()
    val cardStore: CardStore = CardStoreFactory().createCardStore()
    val turnOfPlayer: Player = players.first()
    var firstDiceResult: Int = 0
    var secondDiceResult: Int = 0
    private val random = Random(seed)
    private val dice: Dice = Dice(random.nextLong())


    fun throwDices(option: DiceThrowOption) {
        val number = when (option) {
            DiceThrowOption.USE_ONE -> {
                firstDiceResult = dice.play()
            }
            DiceThrowOption.USE_TWO -> dice.play()
        }
    }

    enum class DiceThrowOption {
        USE_ONE,
        USE_TWO
    }
}
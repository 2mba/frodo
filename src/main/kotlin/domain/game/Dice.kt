package org.tumba.frodo.domain.game

import org.tumba.frodo.domain.game.DiceThrowOption.*
import java.util.*

class Dice(
    val seed: Long = System.currentTimeMillis()
) {

    private val random = Random(seed)

    fun play(): Int {
        return random.nextInt(6).also {
            println("Threw $it")
        }
    }
}

enum class DiceThrowOption {
    USE_ONE,
    USE_TWO
}

class DiceThrowResult private constructor(
    val option: DiceThrowOption,
    val first: Int,
    val second: Int?
) {

    val sum = first + (second ?: 0)

    companion object {

        fun fromOneDiceThrow(first: Int): DiceThrowResult = DiceThrowResult(
            USE_ONE,
            first,
            null
        )

        fun fromTwoDiceThrow(first: Int, second: Int): DiceThrowResult = DiceThrowResult(
            USE_ONE,
            first,
            second
        )
    }
}
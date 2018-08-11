package org.tumba.frodo.domain.game

import java.util.*

class Dice(
    val seed: Long = System.currentTimeMillis()
) {

    private val random = Random(seed)

    fun play(): Int {
        return random.nextInt(6)
    }
}
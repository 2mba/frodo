package org.tumba.frodo.domain.game

data class PlayerState(
    val cards: Cards,
    var coins: Int
)

class PlayerStateFactory {

    fun createInitialState(): PlayerState {
        return PlayerState(
            coins = 3,
            cards = CityFactory().createInitialCards()
        )
    }
}

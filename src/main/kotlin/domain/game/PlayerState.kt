package org.tumba.frodo.domain.game

data class PlayerState(
    val city: City,
    var coins: Int
)

class PlayerStateFactory {

    fun createInitialState(): PlayerState {
        return PlayerState(
            coins = 3,
            city = CityFactory().createInitialCity()
        )
    }
}

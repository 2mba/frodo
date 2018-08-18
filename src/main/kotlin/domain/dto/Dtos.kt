package domain.dto

import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.SightCard

data class PlayerDto(
    val number: Int,
    val name: String
)

data class GameStateDto(
    val turnNumber: Int,
    val players: List<PlayerDto>,
    val playerStates: List<PlayerStateDto>,
    val storeDto: StoreDto
)

data class StoreDto(
    val cards: List<DevelopmentCard>
)

data class PlayerStateDto(
    val playerNumber: Int,
    val cards: List<DevelopmentCard>,
    val sightCards: List<SightCard>,
    val coins: Int
)

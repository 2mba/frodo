package domain.dto

import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.SightCard

data class PlayerDto(
    val id: Int,
    val name: String
)

data class GameStateDto(
    val turnNumber: Int,
    val players: List<PlayerDto>,
    val playerStates: List<PlayerStateDto>,
    val storeDto: StoreDto,
    val diceThrowResult: DiceThrowResultDto?,
    val gameState: GameProcessStateDto,
    val currentPlayerId: Int
)

data class StoreDto(
    val cards: List<DevelopmentCard>
)

data class PlayerStateDto(
    val playerId: Int,
    val cards: List<DevelopmentCard>,
    val sightCards: List<SightCard>,
    val coins: Int
)

class DiceThrowResultDto(
    val first: Int,
    val second: Int?
) {
    val sum = first + (second ?: 0)
}

enum class GameProcessStateDto {
    DiceThrowing,
    CardBuying,
}
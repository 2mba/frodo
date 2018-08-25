package domain.usecase

import io.reactivex.Completable
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.game.GameHolder

class BuyCardUseCase(
    private val card: DevelopmentCard,
    private val gameHolder: GameHolder
): IBuyCardCase {

    override fun execute(): Completable {
        return Completable.fromAction {
            gameHolder.game?.buyCard(card)
        }
    }
}
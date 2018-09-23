package domain.usecase

import io.reactivex.Completable
import org.tumba.frodo.domain.game.GameHolder

class EndTurnUseCase(
    private val gameHolder: GameHolder
): IEndTurnCase {

    override fun execute(): Completable {
        return Completable.fromAction {
            gameHolder.game?.endTurn()
        }
    }
}
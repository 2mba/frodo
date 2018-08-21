package domain.usecase

import io.reactivex.Completable
import org.tumba.frodo.domain.game.DiceThrowOption
import org.tumba.frodo.domain.game.Game
import org.tumba.frodo.domain.usecase.IUseCase

class ThrowDiceUseCase(
    private val game: Game
) : IThrowDiceUseCase {

    override fun execute(): Completable {
        return Completable.fromAction {
            game.throwDices(option = DiceThrowOption.USE_ONE)
        }
    }

}
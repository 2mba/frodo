package domain.usecase

import domain.dto.GameStateDto
import domain.dto.toGameStateDto
import io.reactivex.Observable
import org.tumba.frodo.domain.game.Game

class GetGameStateUseCase(private val game: Game): IGetGameStateUseCase {

    override fun execute(): Observable<GameStateDto> {
        return Observable.just(game.toGameStateDto())
    }
}
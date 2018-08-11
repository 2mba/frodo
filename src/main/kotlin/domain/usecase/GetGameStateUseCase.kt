package domain.usecase

import domain.dto.GameStateDto
import io.reactivex.Observable
import org.tumba.frodo.domain.game.Game

class GetGameStateUseCase(game: Game): IGetGameStateUseCase {

    override fun execute(): Observable<GameStateDto> {
        return Observable.fromCallable {
            GameStateDto(

            )
        }
    }
}
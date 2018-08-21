package domain.usecase

import domain.dto.GameStateDto
import domain.dto.toGameStateDto
import io.reactivex.Observable
import org.tumba.frodo.domain.game.GameHolder

class GetGameStateUseCase(private val gameHolder: GameHolder): IGetGameStateUseCase {

    override fun execute(): Observable<GameStateDto> {
        return gameHolder.gameEventSubject?.map { it.toGameStateDto() }
            ?: Observable.error(IllegalStateException("subject not inited"))
    }
}
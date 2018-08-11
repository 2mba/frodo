package domain.usecase

import domain.dto.GameStateDto
import io.reactivex.Observable
import org.tumba.frodo.domain.usecase.IUseCase

interface IGetGameStateUseCase: IUseCase<Observable<GameStateDto>> {

}
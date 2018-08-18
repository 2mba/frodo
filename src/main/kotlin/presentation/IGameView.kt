package presentation

import domain.dto.GameStateDto

interface IGameView {

    fun showError(msg: String)

    fun onStartGame()

    fun updateGameState(state: GameStateDto)
}
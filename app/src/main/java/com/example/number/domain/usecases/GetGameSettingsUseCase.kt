package com.example.number.domain.usecases

import com.example.number.domain.entity.GameSettings
import com.example.number.domain.entity.Level
import com.example.number.domain.repository.GameRepository

class GetGameSettingsUseCase (
    private val repository: GameRepository
        ){


    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }


}
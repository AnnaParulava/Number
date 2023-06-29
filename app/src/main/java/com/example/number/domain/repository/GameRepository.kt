package com.example.number.domain.repository

import com.example.number.domain.entity.GameSettings
import com.example.number.domain.entity.Level
import com.example.number.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}
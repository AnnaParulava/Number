package com.example.number.domain.entity

import java.net.SocketOptions

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>
)
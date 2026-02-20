package org.mars.application.dto

data class MarsRoverUseCaseResponseDto(
    val x: Int,
    val y: Int,
    val orientation: String,
    val status: String
)
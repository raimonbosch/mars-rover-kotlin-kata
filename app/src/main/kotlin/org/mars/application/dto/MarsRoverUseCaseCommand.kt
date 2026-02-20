package org.mars.application.dto

enum class MarsRoverUseCaseCommand {
    ROTATE_LEFT,
    ROTATE_RIGHT,
    MOVE_FORWARD,
    MOVE_BACKWARD;

    companion object {
        fun fromString(value: String): MarsRoverUseCaseCommand =
            when (value) {
                "l" -> ROTATE_LEFT
                "r" -> ROTATE_RIGHT
                "f" -> MOVE_FORWARD
                "b" -> MOVE_BACKWARD
                else -> throw IllegalArgumentException(
                    "Invalid Mars Rover command"
                )
            }
    }
}
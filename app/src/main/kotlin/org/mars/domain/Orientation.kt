package org.mars.domain

enum class Orientation {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    companion object {
        fun fromString(value: String): Orientation =
            when (value) {
                "n" -> NORTH
                "s" -> SOUTH
                "e" -> EAST
                "w" -> WEST
                else -> throw IllegalArgumentException(
                    "Invalid orientation"
                )
            }
    }

    fun opposite(): Orientation =
        when (this) {
            NORTH -> SOUTH
            SOUTH -> NORTH
            EAST  -> WEST
            WEST  -> EAST
        }

    fun flip90DegreesEast(): Orientation =
        when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH  -> WEST
            WEST  -> NORTH
        }

    fun flip90DegreesWest(): Orientation =
        when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH  -> EAST
            EAST  -> NORTH
        }
}
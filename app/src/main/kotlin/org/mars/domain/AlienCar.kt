package org.mars.domain

interface AlienCar {
    fun rotate(rotateDirection: RotateDirection): AlienCar

    fun move(sourcePosition: Position, targetPosition: Position): AlienCar

    fun currentPositon(): Position

    fun currentOrientation(): Orientation
}
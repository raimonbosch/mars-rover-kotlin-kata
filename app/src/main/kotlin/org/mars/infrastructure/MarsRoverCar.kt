package org.mars.infrastructure

import org.mars.domain.AlienCar
import org.mars.domain.Orientation
import org.mars.domain.Position
import org.mars.domain.RotateDirection

class MarsRoverCar(private var currentPosition: Position, private var currentOrientation: Orientation): AlienCar {
    override fun rotate(rotateDirection: RotateDirection): AlienCar {
        if (rotateDirection === RotateDirection.RIGHT) {
            this.currentOrientation = this.currentOrientation.flip90DegreesEast()
        }

        if (rotateDirection === RotateDirection.LEFT) {
            this.currentOrientation = this.currentOrientation.flip90DegreesWest()
        }

        return this
    }

    override fun move(sourcePosition: Position, targetPosition: Position): AlienCar {
        if (sourcePosition == currentPosition && sourcePosition != targetPosition) {
            this.currentPosition = targetPosition
        }

        return this
    }

    override fun currentPositon(): Position {
        return currentPosition;
    }

    override fun currentOrientation(): Orientation {
        return currentOrientation
    }
}
package org.mars.application

import org.mars.application.dto.MarsRoverUseCaseCommand
import org.mars.application.dto.MarsRoverUseCaseResponseDto
import org.mars.domain.AlienCar
import org.mars.domain.Obstacle
import org.mars.domain.Node
import org.mars.domain.PlanetMapRepository
import org.mars.domain.RotateDirection

class MarsRoverRouteUseCase(
    private var alienCar: AlienCar,
    private var planetMapRepository: PlanetMapRepository
) {
    fun execute(marsRoverRouteCommand: MarsRoverUseCaseCommand): MarsRoverUseCaseResponseDto {
        var nextNode: Node? = null
        when (marsRoverRouteCommand) {
            MarsRoverUseCaseCommand.ROTATE_RIGHT -> {
                alienCar.rotate(RotateDirection.RIGHT)
            }
            MarsRoverUseCaseCommand.ROTATE_LEFT -> {
                alienCar.rotate(RotateDirection.LEFT)
            }
            MarsRoverUseCaseCommand.MOVE_FORWARD -> {
                val currentPosition = alienCar.currentPositon()
                nextNode = planetMapRepository.move(currentPosition, alienCar.currentOrientation())
            }
            MarsRoverUseCaseCommand.MOVE_BACKWARD -> {
                val currentPosition = alienCar.currentPositon()
                nextNode = planetMapRepository.move(currentPosition, alienCar.currentOrientation().opposite())
            }
        }

        if (nextNode !== null) {
            if (nextNode is Obstacle) {
                return MarsRoverUseCaseResponseDto(
                    alienCar.currentPositon().x,
                    alienCar.currentPositon().y,
                    alienCar.currentOrientation().toString(),
                    "Obstacle found"
                )
            }

            alienCar.move(alienCar.currentPositon(), nextNode.positon)
        }

        return MarsRoverUseCaseResponseDto(
            alienCar.currentPositon().x,
            alienCar.currentPositon().y,
            alienCar.currentOrientation().toString(),
            "Car moved"
        )
    }
}
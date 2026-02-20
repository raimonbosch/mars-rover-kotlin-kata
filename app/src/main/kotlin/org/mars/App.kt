package org.mars

import org.mars.application.MarsRoverRouteUseCase
import org.mars.application.PlanetMapGenerationUseCase
import org.mars.application.dto.MarsRoverUseCaseCommand
import org.mars.application.dto.MarsRoverUseCaseResponseDto
import org.mars.domain.AlienCar
import org.mars.domain.Mesh
import org.mars.domain.Obstacle
import org.mars.domain.Orientation
import org.mars.domain.Position
import org.mars.infrastructure.MarsRoverCar
import org.mars.infrastructure.PlanetMapMeshRepository
import java.util.Scanner

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    val reader = Scanner(System.`in`)

    println("Insert horizontal map size:")
    val sizeX = reader.nextInt()

    println("Insert vertical map size:")
    val sizeY = reader.nextInt()

    println("Insert number of obstacles in your map:")
    val numObstacles = reader.nextInt()

    val planetGenerator = PlanetMapGenerationUseCase()
    val mesh = planetGenerator.execute(sizeX, sizeY, numObstacles)

    println("Insert horizontal initial rover position:")
    val roverX = reader.nextInt()

    println("Insert vertical initial rover position:")
    val roverY = reader.nextInt()

    println("Insert initial rover direction (n = north, e = east, s = south left, w = west):")
    val roverDir = reader.next() // n, e, s, w
    val roverOrientation: Orientation = Orientation.fromString(roverDir)

    val marsRoverCar = MarsRoverCar(Position(roverX, roverY), roverOrientation)
    val useCase = MarsRoverRouteUseCase(
        marsRoverCar,
        PlanetMapMeshRepository(mesh)
    )

    println(getStringMapRepresentation(sizeX, sizeY, mesh, marsRoverCar))
    println("Rover is at x:${marsRoverCar.currentPositon().x} y:${marsRoverCar.currentPositon().y} facing:${marsRoverCar.currentOrientation()}")

    while (true) {
        println("Insert command (f = forward, b = backward, l = turn left, r = turn right):")
        val command = reader.next()
        val marsRoverCommand: MarsRoverUseCaseCommand = MarsRoverUseCaseCommand.fromString(command)

        val response: MarsRoverUseCaseResponseDto = useCase.execute(marsRoverCommand)

        println(getStringMapRepresentation(sizeX, sizeY, mesh, marsRoverCar))
        println("Rover is at x:${response.x} y:${response.y} facing:${response.orientation} status:${response.status}")
    }
}

private fun getStringMapRepresentation(width: Int, height: Int, mesh: Mesh, alienCar: AlienCar): String
{
    val response = StringBuilder()
    response.append("=".repeat(width))
    response.append("\n")

    for (y in 0 until height) {
        for (x in 0 until width) {
            val position = Position(x, y)
            if (position == alienCar.currentPositon()) {
                val carOrientation = when (alienCar.currentOrientation()) {
                    Orientation.NORTH -> "N"
                    Orientation.SOUTH -> "S"
                    Orientation.EAST -> "E"
                    Orientation.WEST -> "W"
                }
                response.append(carOrientation)
                continue
            }

            val node = mesh.retrieve(Position(x, y))
            if (node is Obstacle) {
                response.append("O")
            } else {
                response.append("X")
            }
        }
        response.append("\n")
    }

    response.append("=".repeat(width))
    response.append("\n")

    return response.toString()
}

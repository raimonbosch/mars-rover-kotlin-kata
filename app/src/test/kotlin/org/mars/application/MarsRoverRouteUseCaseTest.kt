package org.mars.application

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mars.application.dto.MarsRoverUseCaseCommand
import org.mars.domain.MeshBuilder
import org.mars.domain.Orientation
import org.mars.domain.PlanetMapRepository
import org.mars.domain.Position
import org.mars.infrastructure.MarsRoverCar
import org.mars.infrastructure.PlanetMapMeshRepository
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MarsRoverRouteUseCaseTest {

    private lateinit var sut: MarsRoverRouteUseCase
    private lateinit var planetMapRepository: PlanetMapRepository
    private lateinit var planetMapRepositoryWithObstacle: PlanetMapRepository

    @BeforeEach
    fun setUp() {
        this.planetMapRepository = PlanetMapMeshRepository(MeshBuilder().build(5, 5).getMesh())
        this.planetMapRepositoryWithObstacle = PlanetMapMeshRepository(MeshBuilder().build(5, 5).withObstacleAt(Position(1,0)).getMesh())
    }

    @Test
    fun testMoveToEast() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(1, response.x)
        assertEquals(0, response.y)
        assertEquals(Orientation.EAST.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFrom00ToWest() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("b"))
        assertEquals(4, response.x)
        assertEquals(0, response.y)
        assertEquals(Orientation.EAST.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFrom00ToSouth() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        this.sut.execute(MarsRoverUseCaseCommand.fromString("r"))
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(0, response.x)
        assertEquals(1, response.y)
        assertEquals(Orientation.SOUTH.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFrom00ToNorth() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        this.sut.execute(MarsRoverUseCaseCommand.fromString("l"))
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(0, response.x)
        assertEquals(4, response.y)
        assertEquals(Orientation.NORTH.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFrom00Rotate2Times() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        this.sut.execute(MarsRoverUseCaseCommand.fromString("l"))
        this.sut.execute(MarsRoverUseCaseCommand.fromString("l"))
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(4, response.x)
        assertEquals(0, response.y)
        assertEquals(Orientation.WEST.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFrom00Move2Times() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepository
        )
        this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(2, response.x)
        assertEquals(0, response.y)
        assertEquals(Orientation.EAST.toString(), response.orientation)
        assertEquals("Car moved", response.status)
    }

    @Test
    fun testFromDoNotMoveIfObstacle() {
        this.sut = MarsRoverRouteUseCase(
            MarsRoverCar(
                Position(0, 0),
                Orientation.EAST
            ),
            this.planetMapRepositoryWithObstacle
        )
        val response = this.sut.execute(MarsRoverUseCaseCommand.fromString("f"))
        assertEquals(0, response.x)
        assertEquals(0, response.y)
        assertEquals(Orientation.EAST.toString(), response.orientation)
        assertEquals("Obstacle found", response.status)
    }

    @Test
    fun testInvalidCommand() {
        val exception = assertFailsWith<IllegalArgumentException> {
            MarsRoverUseCaseCommand.fromString("z")
        }

        assert(exception.message == "Invalid Mars Rover command")

    }
}
package org.mars.domain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class MeshBuilderTest {

    private lateinit var sut: MeshBuilder

    @BeforeEach
    fun setUp() {
        sut = MeshBuilder()
    }

    @Test
    fun testMesh() {
        val mesh = sut.build(4, 4).getMesh()

        assertEquals(
            Position(0, 0),
            mesh.retrieve(Position(1, 0)).getNeighbor(Orientation.WEST)
        )
        assertEquals(
            Position(1, 0),
            mesh.retrieve(Position(0, 0)).getNeighbor(Orientation.EAST)
        )

        assertEquals(
            Position(0, 0),
            mesh.retrieve(Position(0, 1)).getNeighbor(Orientation.NORTH)
        )
        assertEquals(
            Position(0, 1),
            mesh.retrieve(Position(0, 0)).getNeighbor(Orientation.SOUTH)
        )

        assertEquals(
            Position(0, 0),
            mesh.retrieve(Position(3, 0)).getNeighbor(Orientation.EAST)
        )
        assertEquals(
            Position(3, 0),
            mesh.retrieve(Position(0, 0)).getNeighbor(Orientation.WEST)
        )

        assertEquals(
            Position(0, 0),
            mesh.retrieve(Position(0, 3)).getNeighbor(Orientation.SOUTH)
        )
        assertEquals(
            Position(0, 3),
            mesh.retrieve(Position(0, 0)).getNeighbor(Orientation.NORTH)
        )
    }

    @Test
    fun testBuildWithRandomObstacles() {
        val mesh = sut.build(2, 2).withObstacles(1).getMesh()
        val nodes: MutableList<Node> = mutableListOf<Node>()
        nodes.add(mesh.retrieve(Position(0, 0)))
        nodes.add(mesh.retrieve(Position(1, 0)))
        nodes.add(mesh.retrieve(Position(0, 1)))
        nodes.add(mesh.retrieve(Position(1, 1)))
        assertTrue(nodes.any { it is Obstacle })
    }

    @Test
    fun testRegularBuildHasNoObstacles() {
        val mesh = sut.build(2, 2).getMesh()
        val nodes: MutableList<Node> = mutableListOf<Node>()
        nodes.add(mesh.retrieve(Position(0, 0)))
        nodes.add(mesh.retrieve(Position(1, 0)))
        nodes.add(mesh.retrieve(Position(0, 1)))
        nodes.add(mesh.retrieve(Position(1, 1)))
        assertTrue(nodes.none { it is Obstacle })
    }

    @Test
    fun testBuildWithObstacleAt() {
        val mesh = sut.build(2, 2).withObstacleAt(Position(0, 1)).getMesh()
        val nodes: MutableList<Node> = mutableListOf<Node>()
        assertTrue(mesh.retrieve(Position(0, 1)) is Obstacle)
    }

    @Test
    fun testUnavailablePosition() {
        val exception = assertFailsWith<IllegalArgumentException> {
            val mesh = sut.build(3, 3).getMesh()
            mesh.retrieve(Position(100, 100))
        }

        assert(exception.message == "Coordinates out of range")
    }
}
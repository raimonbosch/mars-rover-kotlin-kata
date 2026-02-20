package org.mars.domain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NodeTest {
    private lateinit var sut: Node

    @BeforeEach
    fun setUp() {
        sut = Node(Position(0, 0))
    }

    @Test
    fun testUnavailableNeighbor() {
        val exception = assertFailsWith<IllegalStateException> {
            sut.getNeighbor(Orientation.SOUTH)
        }

        assert(exception.message == "No position neighbor for orientation")
    }

    @Test
    fun testConnectNeighbor() {
        sut = Node(Position(0, 0))
        sut.connectNeighbor(Orientation.SOUTH, Position(0, 1))
        assertEquals(
            Position(0, 1),
            sut.getNeighbor(Orientation.SOUTH)
        )
    }
}
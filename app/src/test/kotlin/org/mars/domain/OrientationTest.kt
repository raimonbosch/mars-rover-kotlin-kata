package org.mars.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OrientationTest {
    @Test
    fun testOpposites() {
        assertEquals(
            Orientation.SOUTH,
            Orientation.NORTH.opposite()
        )
        assertEquals(
            Orientation.EAST,
            Orientation.WEST.opposite()
        )
        assertEquals(
            Orientation.NORTH,
            Orientation.SOUTH.opposite()
        )
        assertEquals(
            Orientation.WEST,
            Orientation.EAST.opposite()
        )
    }

    @Test
    fun testFlipsEast() {
        assertEquals(
            Orientation.EAST,
            Orientation.NORTH.flip90DegreesEast()
        )
        assertEquals(
            Orientation.SOUTH,
            Orientation.EAST.flip90DegreesEast()
        )
        assertEquals(
            Orientation.WEST,
            Orientation.SOUTH.flip90DegreesEast()
        )
        assertEquals(
            Orientation.NORTH,
            Orientation.WEST.flip90DegreesEast()
        )
    }

    @Test
    fun testFlipsWest() {
        assertEquals(
            Orientation.WEST,
            Orientation.NORTH.flip90DegreesWest()
        )
        assertEquals(
            Orientation.SOUTH,
            Orientation.WEST.flip90DegreesWest()
        )
        assertEquals(
            Orientation.EAST,
            Orientation.SOUTH.flip90DegreesWest()
        )
        assertEquals(
            Orientation.NORTH,
            Orientation.EAST.flip90DegreesWest()
        )
    }

    @Test
    fun testFromStrings() {
        assertEquals(
            Orientation.NORTH,
            Orientation.fromString("n")
        )
        assertEquals(
            Orientation.EAST,
            Orientation.fromString("e")
        )
        assertEquals(
            Orientation.SOUTH,
            Orientation.fromString("s")
        )
        assertEquals(
            Orientation.WEST,
            Orientation.fromString("w")
        )
    }

    @Test
    fun testInvalidOrientation() {
        val exception = assertFailsWith<IllegalArgumentException> {
            Orientation.fromString("z")
        }

        assert(exception.message == "Invalid orientation")
    }
}
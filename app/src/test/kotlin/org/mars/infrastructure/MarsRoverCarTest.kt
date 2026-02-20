package org.mars.infrastructure

import apple.laf.JRSUIConstants
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mars.domain.AlienCar
import org.mars.domain.Orientation
import org.mars.domain.Position
import org.mars.domain.RotateDirection
import kotlin.test.assertEquals

class MarsRoverCarTest {
    private lateinit var sut: AlienCar

    @BeforeEach
    fun setUp() {
        sut = MarsRoverCar(Position(0, 0), Orientation.EAST)
    }

    @Test
    fun testRotateDirection() {
        sut = sut.rotate(RotateDirection.RIGHT)
        assertEquals(
            Orientation.SOUTH,
            sut.currentOrientation()
        )
        sut = sut.rotate(RotateDirection.LEFT)
        assertEquals(
            Orientation.EAST,
            sut.currentOrientation()
        )
    }

    @Test
    fun testDontMove() {
        sut = sut.move(Position(0, 0), Position(0, 0))
        assertEquals(
            Position(0, 0),
            sut.currentPositon()
        )
    }

    @Test
    fun testDontMoveInvalidTarget() {
        sut = sut.move(Position(100, 100), Position(0, 0))
        assertEquals(
            Position(0, 0),
            sut.currentPositon()
        )
    }

    @Test
    fun testMove() {
        sut = sut.move(Position(0, 0), Position(0, 1))
        assertEquals(
            Position(0, 1),
            sut.currentPositon()
        )
    }
}
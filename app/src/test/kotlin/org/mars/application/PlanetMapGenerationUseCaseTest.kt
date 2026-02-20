package org.mars.application

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mars.domain.Orientation
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class PlanetMapGenerationUseCaseTest {
    private lateinit var sut: PlanetMapGenerationUseCase

    @BeforeEach
    fun setUp() {
        this.sut = PlanetMapGenerationUseCase()
    }

    @Test
    fun testGenerate() {
        val mesh = this.sut.execute(4, 4)
        assertNotNull(mesh)
    }

    @Test
    fun testGenerateWithObstacles() {
        val mesh = this.sut.execute(4, 4, 1)
        assertNotNull(mesh)
    }

    @Test
    fun testInvalidWidth() {
        val exception = assertFailsWith<IllegalArgumentException> {
            this.sut.execute(-1, 4)
        }

        assert(exception.message == "width must be > 0")
    }

    @Test
    fun testInvalidHeight() {
        val exception = assertFailsWith<IllegalArgumentException> {
            this.sut.execute(4, 0)
        }

        assert(exception.message == "height must be > 0")
    }
}
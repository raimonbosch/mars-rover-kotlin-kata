package org.mars.application

import org.mars.domain.Mesh
import org.mars.domain.MeshBuilder

class PlanetMapGenerationUseCase {
    fun execute(width: Int, height: Int, obstacles: Int = 0): Mesh {
        if (width <= 0) {
            throw IllegalArgumentException("width must be > 0")
        }
        if (height <= 0) {
            throw IllegalArgumentException("height must be > 0")
        }

        if (obstacles > 0) {
            return MeshBuilder().build(width, height).withObstacles(obstacles).getMesh()
        }

        return MeshBuilder().build(width, height).getMesh()
    }
}
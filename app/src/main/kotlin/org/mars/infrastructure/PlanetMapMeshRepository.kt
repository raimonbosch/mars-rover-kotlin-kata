package org.mars.infrastructure

import org.mars.domain.Mesh
import org.mars.domain.Node
import org.mars.domain.Orientation
import org.mars.domain.PlanetMapRepository
import org.mars.domain.Position

class PlanetMapMeshRepository(private val mesh: Mesh): PlanetMapRepository  {
    override fun move(position: Position, orientation: Orientation): Node {
        val node = mesh.retrieve(position)
        val positionNeighbor = node.getNeighbor(orientation)
        return mesh.retrieve(positionNeighbor);
    }
}
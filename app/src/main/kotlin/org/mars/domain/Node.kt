package org.mars.domain

open class Node(
    var positon: Position,
    var neighbors: MutableMap<Orientation, Position> = mutableMapOf()
) {
    fun connectNeighbor(orientation: Orientation, position: Position) {
        neighbors[orientation] = position
    }

    fun getNeighbor(orientation: Orientation): Position {
        return neighbors[orientation]
            ?: throw IllegalStateException("No position neighbor for orientation")
    }
}
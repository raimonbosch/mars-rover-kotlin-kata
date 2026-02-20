package org.mars.domain

class Mesh(
    private val nodes: List<Node>
) {
    fun retrieve(position: Position): Node {
        return nodes.firstOrNull { it.positon == position }
            ?: throw IllegalArgumentException("Coordinates out of range")
    }
}
package org.mars.domain

interface PlanetMapRepository {
    fun move(position: Position, orientation: Orientation): Node;
}
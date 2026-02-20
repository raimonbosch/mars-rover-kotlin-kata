package org.mars.domain

class Obstacle(position: Position, neighbors: MutableMap<Orientation, Position>) : Node(position, neighbors) {
}
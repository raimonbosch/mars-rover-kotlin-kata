package org.mars.domain

class MeshBuilder {

    private var width: Int = 0
    private var height: Int = 0
    private lateinit var nodes: MutableList<Node>

    fun build(width: Int, height: Int): MeshBuilder {
        this.width = width
        this.height = height

        // Create nodes
        this.nodes = (0 until width).flatMap { x ->
            (0 until height).map { y -> Node(Position(x, y)) }
        }.toMutableList()

        makeConnections()

        return this
    }

    fun withObstacles(numObstacles: Int): MeshBuilder {
        val obstacles = (0..nodes.size - 1).shuffled().take(numObstacles)
        for (i in obstacles) {
            this.nodes[i] = Obstacle(
                this.nodes[i].positon,
                this.nodes[i].neighbors
            )
        }

        return this
    }

    fun withObstacleAt(position: Position): MeshBuilder {
        nodes.forEachIndexed { i, node ->
            if (node.positon == position) {
                this.nodes[i] = Obstacle(
                    this.nodes[i].positon,
                    this.nodes[i].neighbors
                )
            }
        }

        return this
    }

    fun getMesh(): Mesh {
        return Mesh(this.nodes.toList())
    }

    private fun makeConnections() {
        this.nodes.forEach { node1 ->
            this.nodes.forEach { node2 ->
                if (areHorizontallyConnected(node1, node2)) {
                    node1.connectNeighbor(Orientation.EAST, node2.positon)
                    node2.connectNeighbor(Orientation.WEST, node1.positon)
                }

                if (areVerticallyConnected(node1, node2)) {
                    node1.connectNeighbor(Orientation.SOUTH, node2.positon)
                    node2.connectNeighbor(Orientation.NORTH, node1.positon)
                }

                if (areHorizontallyPoleConnected(node1, node2)) {
                    node1.connectNeighbor(Orientation.WEST, node2.positon)
                    node2.connectNeighbor(Orientation.EAST, node1.positon)
                }

                if (areVerticallyPoleConnected(node1, node2)) {
                    node1.connectNeighbor(Orientation.NORTH, node2.positon)
                    node2.connectNeighbor(Orientation.SOUTH, node1.positon)
                }
            }
        }
    }

    private fun areHorizontallyConnected(node1: Node, node2: Node) =
        node1.positon.x == node2.positon.x - 1 && node1.positon.y == node2.positon.y

    private fun areHorizontallyPoleConnected(node1: Node, node2: Node) =
        node1.positon.x == 0 && node2.positon.x == width - 1 && node1.positon.y == node2.positon.y

    private fun areVerticallyConnected(node1: Node, node2: Node) =
        node1.positon.y == node2.positon.y - 1 && node1.positon.x == node2.positon.x

    private fun areVerticallyPoleConnected(node1: Node, node2: Node) =
        node1.positon.y == 0 && node2.positon.y == height - 1 && node1.positon.x == node2.positon.x
}
package day6

import java.io.File

fun main() {
    challengeOne()
}

enum class TileState {
    EMPTY,
    OBSTACLE
}

enum class GuardDirection {
    UP,
    RIGHT,
    DOWN,
    LEFT
}

class Tile(val x: Int, val y: Int, private val state: TileState) {
    private var visited: Boolean = false

    override fun toString(): String {
        return when(state){
            TileState.EMPTY -> if(visited) "X" else "."
            TileState.OBSTACLE -> "#"
        }
    }

    fun markAsVisited() {
        visited = true
    }

    fun isObstacle(): Boolean {
        return state == TileState.OBSTACLE
    }

    fun hasBeenVisited() : Boolean {
        return visited
    }
}

class Guard(private var x: Int, private var y: Int, private var direction: GuardDirection) {
    companion object {
        fun createGuard(x: Int, y: Int, direction: GuardDirection, map: Map) : Guard {
            map.tileAt(x,y).markAsVisited()
            return Guard(x,y,direction)
        }
    }
    var hasLeftTheMap = false
    fun advance(map: Map) {
        if(hasLeftTheMap) return

        val currentTile = map.tileAt(x, y)
        val nextTile = calculateNextTile(currentTile, direction, map)
        nextTile.markAsVisited()
        x = nextTile.x
        y = nextTile.y
    }

    fun isAt(x: Int, y:Int) : Boolean {
        return this.x == x && this.y == y
    }

    private fun calculateNextTile(currentTile: Tile, direction: GuardDirection, map: Map) : Tile {
        this.direction = direction
        val movement = when (direction) {
            GuardDirection.UP -> arrayOf(-1,0)
            GuardDirection.RIGHT -> arrayOf(0,1)
            GuardDirection.DOWN -> arrayOf(1,0)
            GuardDirection.LEFT -> arrayOf(0,-1)
        }
        val nextX = currentTile.x + movement[0]
        val nextY = currentTile.y + movement[1]
        val isNextOutsideOfMap = nextX < 0 || nextX >= map.rows || nextY < 0 || nextY >= map.columns
        if(isNextOutsideOfMap){
            hasLeftTheMap = true
            return currentTile
        }

        val nextTile = map.tileAt(nextX, nextY)
        if(nextTile.isObstacle()){
            val nextDirection = when(direction){
                GuardDirection.UP -> GuardDirection.RIGHT
                GuardDirection.RIGHT -> GuardDirection.DOWN
                GuardDirection.DOWN -> GuardDirection.LEFT
                GuardDirection.LEFT -> GuardDirection.UP
            }
            return calculateNextTile(currentTile,nextDirection,map)
        }

        return nextTile
    }

    fun print() : String {
        return when(direction) {
            GuardDirection.UP -> "^"
            GuardDirection.RIGHT -> ">"
            GuardDirection.LEFT -> "<"
            GuardDirection.DOWN -> "V"
        }
    }
}

class Map(val map: Array<Array<Tile>>, val rows: Int, val columns: Int) {
    companion object {
        fun createMap(map: Array<Array<Tile>>) : Map{
            return Map(map, map.size, map.get(0).size)
        }
    }

    fun tileAt(x: Int, y: Int): Tile {
        return map[x][y]
    }
}

fun challengeOne() {
    val map = Map.createMap(readMap())
    val guard = Guard.createGuard(90,66, GuardDirection.UP, map)
    printState(map, guard)

    while (!guard.hasLeftTheMap) {
        guard.advance(map)
    }
    printState(map, guard)

    println(map.map
        .flatMap { it.map { it.hasBeenVisited() } }.count { it })
}

fun printState(map: Map, guard:Guard) {
    println("-".repeat(map.rows))
    for (x in map.map.indices) {
        for (y in 0..<map.map[x].size) {
            if(guard.isAt(x,y)){
                print(guard.print())
            }else {
                print(map.tileAt(x,y).toString())
            }
        }
        println()
    }
}

fun readMap(): Array<Array<Tile>> {
    val map = File("day6/input.txt")
        .readLines()
        .mapIndexed { rowIndex: Int, s: String ->
            s.toCharArray().mapIndexed { colIndex: Int, c: Char ->
                Tile(rowIndex, colIndex, if (c == '#') TileState.OBSTACLE else TileState.EMPTY)
            }.toTypedArray()
        }
        .toTypedArray()
    return map
}
package advent.of.code

import java.io.File

private enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

private val PIPES =
    mapOf(
        '|' to listOf(Direction.NORTH, Direction.SOUTH),
        '-' to listOf(Direction.EAST, Direction.WEST),
        'L' to listOf(Direction.NORTH, Direction.EAST),
        'J' to listOf(Direction.NORTH, Direction.WEST),
        'F' to listOf(Direction.SOUTH, Direction.EAST),
        '7' to listOf(Direction.SOUTH, Direction.WEST),
        'S' to listOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST),
        '.' to listOf()
    )

private fun findInMaze(maze: List<String>, symbol: Char): Pair<Int, Int> {
    for (i in maze.indices) {
        for (j in 0..<maze[0].length) {
            if (maze[i][j] == symbol) {
                return Pair(i, j)
            }
        }
    }
    return Pair(-1, -1)
}

private fun oppposite(direction: Direction): Direction {
    return when (direction) {
        Direction.NORTH -> Direction.SOUTH
        Direction.SOUTH -> Direction.NORTH
        Direction.WEST -> Direction.EAST
        Direction.EAST -> Direction.WEST
    }
}

private fun move(direction: Direction,curr:Pair<Int,Int>):Pair<Int,Int>{
    return when (direction) {
        Direction.NORTH -> Pair(curr.first-1,curr.second)
        Direction.SOUTH -> Pair(curr.first+1,curr.second)
        Direction.WEST -> Pair(curr.first,curr.second-1)
        Direction.EAST -> Pair(curr.first,curr.second+1)
    }
}

private fun isInBounds(loc: Pair<Int,Int>,maze:List<String>):Boolean{
    return loc.first in maze.indices && loc.second in 0..<maze[0].length
}

private fun processMaze(maze: List<String>) {
    val visited = maze.map { row -> row.toCharArray().map { _ -> false }.toMutableList() }
    val distance = maze.map { row -> row.toCharArray().map { _ -> -1 }.toMutableList() }
    val start = findInMaze(maze, 'S')
    val queue = ArrayDeque<Pair<Int, Int>>()
    queue.add(start)
    visited[start.first][start.second] = true
    distance[start.first][start.second] = 0
    while (queue.isNotEmpty()) {
        val temp = queue.removeFirst()
        val dist = distance[temp.first][temp.second]
        val char = maze[temp.first][temp.second]
        for(dir in PIPES[char]!!){
            val next = move(dir,temp)
            if(isInBounds(next,maze) && !visited[next.first][next.second] && PIPES[maze[next.first][next.second]]?.contains(
                    oppposite(dir))== true
            ){
                visited[next.first][next.second]=true
                distance[next.first][next.second]=dist+1
                queue.add(next)
            }
        }
    }
    println("Max distance: ${distance.map{d->d.max()}.max()}")
}

fun main() {
    try {
        val fileName = "./data/day10.txt"
        processMaze(File(fileName).readLines())
    } catch (e: Exception) {
        println(e.message)
    }

}
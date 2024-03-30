package advent.of.code

import java.io.File
import kotlin.math.max

private var visited = mutableMapOf<Pair<Int,Int>,List<Heading>>()
private enum class Heading {
    LEFT, RIGHT, UP, DOWN, NONE
}

private fun outputHeading(input: Heading, contraption: Char): List<Heading> {
    val output = mutableListOf<Heading>()
    when (contraption) {
        '.' -> output.add(input)
        '-' -> if (input == Heading.LEFT || input == Heading.RIGHT) output.add(input) else {
            output.add(Heading.RIGHT)
            output.add(Heading.LEFT)
        }

        '|' -> if (input == Heading.UP || input == Heading.DOWN) output.add(input) else {
            output.add(Heading.UP)
            output.add(Heading.DOWN)
        }

        '\\' -> when (input) {
            Heading.LEFT -> output.add(Heading.UP)
            Heading.RIGHT -> output.add(Heading.DOWN)
            Heading.UP -> output.add(Heading.LEFT)
            Heading.DOWN -> output.add(Heading.RIGHT)
            Heading.NONE -> Unit
        }

        '/' -> when (input) {
            Heading.LEFT -> output.add(Heading.DOWN)
            Heading.RIGHT -> output.add(Heading.UP)
            Heading.UP -> output.add(Heading.RIGHT)
            Heading.DOWN -> output.add(Heading.LEFT)
            Heading.NONE -> Unit
        }
    }
    return output
}

private fun outputLocation(loc: Pair<Int, Int>, headings: List<Heading>): List<Pair<Int, Int>> {
    val locs = mutableListOf<Pair<Int, Int>>()
    for (heading in headings) {
        when (heading) {
            Heading.LEFT -> locs.add(Pair(loc.first - 1, loc.second))
            Heading.RIGHT -> locs.add(Pair(loc.first + 1, loc.second))
            Heading.UP -> locs.add(Pair(loc.first, loc.second - 1))
            Heading.DOWN -> locs.add(Pair(loc.first, loc.second + 1))
            Heading.NONE -> Unit
        }
    }
    return locs
}

private fun floodFillLayout(
    heading: Heading,
    loc: Pair<Int, Int>,
    layout: List<String>
) {
    if (loc.first !in 0..<layout[0].length || loc.second !in layout.indices) return
    if (loc in visited.keys && visited[loc]?.contains(heading) == true ) return
    if(loc in visited.keys) visited[loc]?.addLast(heading)
    else visited[loc]= mutableListOf(heading)
    val headings = outputHeading(heading, layout[loc.second][loc.first])
    val locs = outputLocation(loc, headings)
    for (i in headings.indices) {
        floodFillLayout(headings[i], locs[i], layout)
    }
}


private fun processLayout(layout: List<String>,loc:Pair<Int,Int>,heading: Heading): Int {
    visited.clear()
    floodFillLayout(heading, loc, layout)
    return visited.size
}

private fun maximumEnergisation(layout:List<String>):Int{
    println(processLayout(layout,Pair(0,0),Heading.RIGHT))
    var maxCount = 0
    for(i in 0..<layout[0].length){
        val temp1 = processLayout(layout,Pair(i,0),Heading.DOWN)
        val temp2 = processLayout(layout,Pair(i,layout.size-1),Heading.UP)
        maxCount = max(maxCount, max(temp1,temp2))
    }
    for(i in 0..<layout.size){
        val temp1 = processLayout(layout,Pair(0,i),Heading.RIGHT)
        val temp2 = processLayout(layout,Pair(layout[0].length-1,i),Heading.LEFT)
        maxCount = max(maxCount, max(temp1,temp2))
    }
    return maxCount
}

fun main() {
    val filename = "./data/day16.txt"
    // part 1
    try {
        println(maximumEnergisation(File(filename).readLines()))
    } catch (e: Exception) {
        print(e.message)
    }
}
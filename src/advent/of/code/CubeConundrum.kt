package advent.of.code

import java.io.File

//https://adventofcode.com/2023/day/1

private fun processString1(record: String, loaded: Map<String, Int>): Int {
    val game = Regex("\\d+").find(record.split(": ")[0])?.value?.toInt()
    for (set in record.trim().split(": ")[1].split("; ")) {
        for (cubes in set.split(", ")) {
            val count = Regex("\\d+").find(cubes)?.value?.toInt()
            val color = Regex("\\D+").find(cubes)?.value?.trim()
            if (count!! > loaded[color]!!) {
                return 0
            }
        }
    }
    return game!!
}

private fun processString2(record: String): Int {
    val loaded = mutableMapOf<String, Int>()
    for (set in record.trim().split(": ")[1].split("; ")) {
        for (cubes in set.split(", ")) {
            val count = Regex("\\d+").find(cubes)?.value?.toInt()
            val color = Regex("\\D+").find(cubes)?.value?.trim()
            if (!loaded.keys.contains(color) || loaded[color]!! < count!!) {
                loaded[color!!] = count!!
            }
        }
    }
    return loaded.values.reduce { prod, count -> prod * count }
}

fun main() {
    val filename = "./data/day2.txt"
    // part 1
    try {
        val loaded = mapOf("red" to 12, "green" to 13, "blue" to 14)
        val answer = File(filename).readLines().sumOf { record -> processString1(record, loaded) }
        println(answer)
    } catch (e: Exception) {
        print(e.message)
    }
    // part 2
    try {
        val answer = File(filename).readLines().sumOf { record -> processString2(record) }
        println(answer)
    } catch (e: Exception) {
        print(e.message)
    }
}
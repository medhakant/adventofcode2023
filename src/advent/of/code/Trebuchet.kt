package advent.of.code

import java.io.File

//https://adventofcode.com/2023/day/1

private fun processString1(cv: String): Int {
    val digits = cv.filter { c -> c.isDigit() }.map { c -> c.digitToInt() }
    return digits.first() * 10 + digits.last()
}

private fun processString2(cv: String): Int {
    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    val digits = ArrayList<Int>()
    for (i in 0..<cv.length) {
        if (cv[i].isDigit()) {
            digits.add(cv[i].digitToInt())
        } else {
            val match = numbers.keys.filter { num -> cv.substring(i).startsWith(num) }
            if (match.isNotEmpty()) {
                numbers[match.first()]?.let { digits.add(it) }
            }
        }
    }
    return digits.first() * 10 + digits.last()
}

fun main() {
    val filename = "./data/day1.txt"
    // part 1
    try {
        val answer = File(filename).readLines().sumOf { cv -> processString1(cv) }
        println(answer)
    } catch (e: Exception) {
        print(e.message)
    }
    // part 2
    try {
        val answer = File(filename).readLines().sumOf { cv -> processString2(cv) }
        println(answer)
    } catch (e: Exception) {
        print(e.message)
    }
}
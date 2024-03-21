package advent.of.code

import java.io.File

private val MULTIPLIER = 1
private fun numWaysToWin(time:Long,distance:Long):Long{
    var count = 0L
    for(i in 0..time){
        if((time-i)*i>distance){
            count++
        }
    }
    return count
}

private fun processInputs1(lines: List<String>):Long{
    val times = Regex("\\d+").findAll(lines[0]).map{ v->v.value.toLong()}.toList()
    val distances = Regex("\\d+").findAll(lines[1]).map{ v->v.value.toLong()}.toList()
    return times.mapIndexed{ i,time -> numWaysToWin(time,distances[i]) }.reduce{prod,value->prod*value}
}

private fun processInputs2(lines: List<String>):Long{
    val time = Regex("\\d+").findAll(lines[0]).map{match->match.value}.joinToString("").toLong()
    val distance = Regex("\\d+").findAll(lines[1]).map{match->match.value}.joinToString("").toLong()
    return numWaysToWin(time, distance)
}
fun main() {
    val filename = "./data/day6.txt"
    // part 1
    try {
        println(processInputs1(File(filename).readLines()))
    } catch (e: Exception) {
        print(e.message)
    }

    // part 2
    try {
        println(processInputs2(File(filename).readLines()))
    } catch (e: Exception) {
        print(e.message)
    }
}
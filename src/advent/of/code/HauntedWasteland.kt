package advent.of.code

import java.io.File


private fun processMap1(map:List<String>):Int{
    val instruction = map[0].trim()
    var steps = 0
    val nodeMap = mutableMapOf<String,Pair<String,String>>()
    for(i in 2..<map.size){
        val parser = Regex("[A-Z]+").findAll(map[i]).map { it->it.value }.toList()
        nodeMap[parser[0]]=Pair(parser[1],parser[2])
    }
    var current = "AAA"
    val end = "ZZZ"
    while(current!=end){
        val index = steps%instruction.length
        if(instruction[index]=='L'){
            current = nodeMap[current]?.first ?: ""
        }else{
            current = nodeMap[current]?.second ?: ""
        }
        steps++
    }
    return steps
}

private fun gcd(x: Long, y: Long): Long {
    return if (y == 0L) x else gcd(y, x % y)
}

private fun processMap2(map:List<String>):Long{
    val instruction = map[0].trim()
    var steps = 0
    val nodeMap = mutableMapOf<String,Pair<String,String>>()
    for(i in 2..<map.size){
        val parser = Regex("[A-Z0-9]+").findAll(map[i]).map { it->it.value }.toList()
        nodeMap[parser[0]]=Pair(parser[1],parser[2])
    }
    var current = nodeMap.keys.filter { s->s.endsWith("A") }.toList()
    var least_steps = current.map { _->0 }.toMutableList()
    var cycle_length = current.map { _->0 }.toMutableList()
    while(cycle_length.contains(0)){
        for(i in 0..<least_steps.size){
            if(current[i].endsWith("Z") && least_steps[i]!=0 && cycle_length[i]==0){
                cycle_length[i]=steps-least_steps[i]
            }else if(current[i].endsWith("Z") && least_steps[i]==0){
                least_steps[i]=steps
            }
        }
        val index = steps%instruction.length
        if(instruction[index]=='L'){
            current = current.parallelStream().map {n-> nodeMap[n]?.first ?:""    }.toList()
        }else{
            current = current.parallelStream().map {n-> nodeMap[n]?.second ?:""    }.toList()
        }
        steps++
    }
    var lcm = 1L
    for(v in cycle_length){
        lcm = (lcm*v)/gcd(v.toLong(),lcm)
    }
    return lcm
}
fun main() {
    try {
        val fileName = "./data/day8.txt"
        print(processMap1(File(fileName).readLines()))
    } catch (e: Exception) {
        println(e.message)
    }

    try {
        val fileName = "./data/day8.txt"
        print(processMap2(File(fileName).readLines()))
    } catch (e: Exception) {
        println(e.message)
    }

}
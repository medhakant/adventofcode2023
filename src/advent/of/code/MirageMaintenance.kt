package advent.of.code

import java.io.File

private fun nextNumber(sequence:List<Int>):Int{
    if(sequence.all { n->n==0 }){
        return 0
    }
    val newSequence = mutableListOf<Int>()
    for(i in 1..<sequence.size){
        newSequence.add(sequence[i]-sequence[i-1])
    }
    return sequence.last()+ nextNumber(newSequence)
}
private fun processSequence1(line:String):Int{
    val sequence = Regex("-?[0-9]+").findAll(line).map{match->match.value.toInt()}.toList()
    return nextNumber(sequence)
}

private fun prevNumber(sequence:List<Int>):Int{
    if(sequence.all { n->n==0 }){
        return 0
    }
    val newSequence = mutableListOf<Int>()
    for(i in 1..<sequence.size){
        newSequence.add(sequence[i]-sequence[i-1])
    }
    return sequence.first()- prevNumber(newSequence)
}
private fun processSequence2(line:String):Int{
    val sequence = Regex("-?[0-9]+").findAll(line).map{match->match.value.toInt()}.toList()
    return prevNumber(sequence)
}
fun main() {
    try {
        val fileName = "./data/day9.txt"
        println(File(fileName).readLines().map{line->processSequence1(line)}.sum())
    } catch (e: Exception) {
        println(e.message)
    }

    try {
        val fileName = "./data/day9.txt"
        println(File(fileName).readLines().map{line->processSequence2(line)}.sum())
    } catch (e: Exception) {
        println(e.message)
    }

}
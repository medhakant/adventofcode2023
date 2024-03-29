package advent.of.code

import java.io.File

private fun getRecordForCondition(condition:String):String{
    val record = mutableListOf<Int>()
    var sum=0
    for (c in condition.toCharArray()){
        if(c=='#')
            sum++
        else if(c=='.')
        {
            if (sum>0) record.add(sum)
            sum=0
        }else{
            break
        }
    }
    if (sum>0) record.add(sum)
    return record.joinToString(separator = ",")
}

private fun isPossible(condition: String,record: String):Boolean{
    if(getRecordForCondition(condition).isEmpty()) return true
    val r1= getRecordForCondition(condition).split(",").map { s->s.toInt() }
    if(r1.size<2)return true
    val r2= record.split(",").map { s->s.toInt() }
    if(r2.size<r1.size) return false
    for(i in 0..<r1.size-1){
        if(r1[i]!=r2[i]) return false
    }
    return r2.sum()-r1.sum() <= Regex("/?").findAll(condition).map{ m->m.value}.toList().size
}

private fun numPossibility(condition:String,record:String):Int{
    if(!condition.contains("?")){
        return if(record== getRecordForCondition(condition)) 1 else 0
    }else if(!isPossible(condition,record)){
        return 0
    }else{
        return numPossibility(condition.replaceFirst('?','.'),record)+numPossibility(condition.replaceFirst('?','#'),record)
    }
}
private fun processLine1(line:String):Int{
    val condition = line.split(" ")[0].trim()
    val record = line.split(" ")[1].trim()
    return numPossibility(condition,record)
}

private fun processLine2(line:String):Int{
    val condition = line.split(" ")[0].trim()
    val record = line.split(" ")[1].trim()
    var newCondition=""
    var newRecord=""
    for(i in 0..4){
        newCondition += condition
        newRecord += record
        if(i!=4){
            newCondition+='?'
            newRecord+=','
        }
    }
    return numPossibility(newCondition,newRecord)
}
fun main() {
    val filename = "./data/day12.txt"
    // part 1
    try {
        println(File(filename).readLines().map { line-> processLine1(line) }.sum())
    } catch (e: Exception) {
        print(e.message)
    }

    // part 2
    try {
        println(File(filename).readLines().parallelStream().map { line-> processLine2(line) }.toList().sum())
    } catch (e: Exception) {
        print(e.message)
    }
}
package advent.of.code
import java.io.File

private fun isPartNumber(row: Int,firstIndex: Int,lastIndex:Int,schematic: List<String>):Boolean{
  val maxRows = schematic.size
  val maxCols = schematic[0].length
  for(i in maxOf(0,row-1)..minOf(maxRows-1,row+1)){
    for(j in maxOf(0,firstIndex-1)..minOf(maxCols-1,lastIndex+1)){
        if(!schematic[i][j].isDigit() && schematic[i][j]!='.'){
          return true
        }
    }
  }
  return false
}
private fun processSchematic1(schematic: List<String>):Int{
  var sum=0
  for(i in 0..<schematic.size){
    val matches = Regex("\\d+").findAll(schematic[i])
    for (match in matches){
      if(isPartNumber(i,match.range.first,match.range.last,schematic)){
        sum += match.value.toInt()
      }
    }
  }
  return sum
}

private fun getGearRation(row:Int,col:Int,schematic: List<String>):Int{
  var gears = mutableListOf<Int>()
  for(k in maxOf(0,row-1)..minOf(row+1,schematic.size-1)){
    val matches = Regex("\\d+").findAll(schematic[k])
    for(match in matches){
      if(col in match.range.first-1..match.range.last+1){
        gears.add(match.value.toInt())
      }
    }
  }
  if(gears.size==2){
    return gears[0]*gears[1]
  }
  return 0
}
private fun processSchematic2(schematic: List<String>):Int{
  var sum=0
  for(i in 0..<schematic.size){
    for(j in 0..<schematic[i].length){
      if(schematic[i][j]=='*'){
        sum+=getGearRation(i,j,schematic)
      }
    }
  }
  return sum
}
fun main(args: Array<String>) {
  try {
    val fileName = "./data/day3.txt"
    val schematic = File(fileName).readLines()
    println(processSchematic1(schematic))
  }catch (e: Exception){
    println(e.message)
  }

  try {
    val fileName = "./data/day3.txt"
    val schematic = File(fileName).readLines()
    println(processSchematic2(schematic))
  }catch (e: Exception){
    println(e.message)
  }

}
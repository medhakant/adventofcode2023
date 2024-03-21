package advent.of.code

import java.io.File
import kotlin.math.pow

private fun processCards1(card:String):Int{
  val winning = Regex("\\d+").findAll(card.split(":")[1].trim().split("|")[0]).map { m->m.value.toInt() }.toList()
  val mine =  Regex("\\d+").findAll(card.split(":")[1].trim().split("|")[1]).map { m->m.value.toInt() }.toList()
  return mine.filter { i->i in winning }.count()
}

private fun processCards2(cards:List<String>):Int{
  val points= cards.map { card->processCards1(card) }.toList()
  var copies = cards.map{1}.toMutableList()
  for(i in 0..<points.size){
    for(j in i+1..minOf(i+points[i],points.size-1)){
      copies[j]+=copies[i]
    }
  }

  return copies.sum()
}
fun main(args: Array<String>) {
  try {
    val fileName = "./data/day4.txt"
    println(File(fileName).readLines().map { card->2.0.pow(processCards1(card)-1).toInt() }.sum())
  }catch (e: Exception){
    println(e.message)
  }

  try {
    val fileName = "./data/day4.txt"
    println(processCards2(File(fileName).readLines()))
  }catch (e: Exception){
    println(e.message)
  }

}